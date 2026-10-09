package com.shopika.app.data.remote

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class SupabaseCloudInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Jika mode bukan SUPABASE_CLOUD, lewatkan tanpa modifikasi
        if (ApiConfig.activeMode != BackendMode.SUPABASE_CLOUD) {
            return chain.proceed(request)
        }

        // Jika URL atau Key belum diisi, kembalikan HTTP 400 terstruktur
        if (!ApiConfig.isCloudConfigured()) {
            val jsonError = """
                {
                    "error": "Konfigurasi Supabase Cloud belum diatur.",
                    "message": "Tambahkan supabase.url dan supabase.anon.key pada file local.properties proyek Anda."
                }
            """.trimIndent()
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(400)
                .message("Supabase Config Missing")
                .body(jsonError.toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull()))
                .build()
        }

        val originalUrl = request.url
        val path = originalUrl.encodedPath
        val method = request.method
        val baseSupabase = ApiConfig.SUPABASE_URL.trimEnd('/') + "/rest/v1"

        // Transformasi rute /api/* ke PostgREST Supabase
        val targetUrlString = when {
            path == "/api/products" && method == "GET" -> {
                val search = originalUrl.queryParameter("search")
                val category = originalUrl.queryParameter("category")
                val sb = StringBuilder("$baseSupabase/products?select=*")
                if (!search.isNullOrBlank()) {
                    sb.append("&or=(name.ilike.*$search*,description.ilike.*$search*)")
                }
                if (!category.isNullOrBlank() && category != "Semua") {
                    sb.append("&category=eq.$category")
                }
                sb.toString()
            }
            path.startsWith("/api/products/") && method == "GET" -> {
                val id = path.removePrefix("/api/products/")
                "$baseSupabase/products?id=eq.$id&select=*"
            }
            path == "/api/cart" && method == "GET" -> {
                "$baseSupabase/cart_items?select=*"
            }
            path == "/api/cart" && method == "POST" -> {
                "$baseSupabase/cart_items"
            }
            path.startsWith("/api/cart/") && (method == "PUT" || method == "PATCH") -> {
                val id = path.removePrefix("/api/cart/")
                "$baseSupabase/cart_items?id=eq.$id"
            }
            path.startsWith("/api/cart/") && method == "DELETE" -> {
                val id = path.removePrefix("/api/cart/")
                "$baseSupabase/cart_items?id=eq.$id"
            }
            path == "/api/checkout" && method == "POST" -> {
                "$baseSupabase/orders"
            }
            path == "/api/payment/simulate" && method == "POST" -> {
                val orderId = originalUrl.queryParameter("order_id") ?: ""
                "$baseSupabase/orders?id=eq.$orderId"
            }
            path == "/api/orders" && method == "GET" -> {
                "$baseSupabase/orders?select=*&order=created_at.desc"
            }
            path.startsWith("/api/orders/") && method == "GET" -> {
                val id = path.removePrefix("/api/orders/")
                "$baseSupabase/orders?id=eq.$id&select=*"
            }
            else -> originalUrl.toString()
        }

        val newHttpUrl = targetUrlString.toHttpUrlOrNull() ?: originalUrl

        val newRequestBuilder = request.newBuilder()
            .url(newHttpUrl)
            .addHeader("apikey", ApiConfig.SUPABASE_ANON_KEY)
            .addHeader("Authorization", "Bearer ${ApiConfig.SUPABASE_ANON_KEY}")
            .addHeader("Prefer", "return=representation")
            .addHeader("Content-Type", "application/json")

        // Jika operasi update pada PostgREST menggunakan PATCH
        if (path.startsWith("/api/cart/") && method == "PUT") {
            newRequestBuilder.method("PATCH", request.body)
        }

        return chain.proceed(newRequestBuilder.build())
    }
}
