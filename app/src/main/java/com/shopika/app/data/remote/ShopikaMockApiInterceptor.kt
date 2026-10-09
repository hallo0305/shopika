package com.shopika.app.data.remote

import com.google.gson.Gson
import com.shopika.app.data.model.AddToCartRequest
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.model.CheckoutRequest
import com.shopika.app.data.model.DeleteResponse
import com.shopika.app.data.model.OrderDto
import com.shopika.app.data.model.PaymentResponse
import com.shopika.app.data.model.ProductDto
import com.shopika.app.data.model.UpdateCartQuantityRequest
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class ShopikaMockApiInterceptor : Interceptor {

    private val gson = Gson()

    // Database produk awal
    private val productCatalog = mutableListOf(
        ProductDto(
            id = "prod-1",
            name = "Wireless Noise-Cancelling Headphones Pro",
            description = "Headphone nirkabel premium dengan fitur Active Noise Cancellation (ANC), driver audio 40mm hi-res, daya tahan baterai hingga 30 jam, dan bantalan telinga busa memori ultra nyaman.",
            price = 499000,
            originalPrice = 799000,
            discountPercent = 38,
            category = "Elektronik",
            imageUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80",
            rating = 4.9,
            soldCount = 420,
            stock = 25,
            variants = listOf("Matte Black", "Silver Gray", "Coral Cream"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-2",
            name = "Smartwatch AMOLED Fitness Tracker",
            description = "Smartwatch layar AMOLED sentuh 1.43 inci, pemantau detak jantung 24/7, SpO2, GPS internal, water resistant 5ATM, dan lebih dari 100 mode olahraga.",
            price = 349000,
            originalPrice = 550000,
            discountPercent = 36,
            category = "Elektronik",
            imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=80",
            rating = 4.8,
            soldCount = 850,
            stock = 40,
            variants = listOf("Obsidian Black", "Desert Sand", "Ocean Blue"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-3",
            name = "Jaket Hoodie Streetwear Minimalis",
            description = "Hoodie berbahan katun fleece 330 gsm yang lembut, tebal, dan hangat. Desain oversized modern khas streetwear kontemporer dengan jahitan rantai rapi.",
            price = 189000,
            originalPrice = 250000,
            discountPercent = 24,
            category = "Fashion",
            imageUrl = "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=600&auto=format&fit=crop&q=80",
            rating = 4.7,
            soldCount = 1200,
            stock = 60,
            variants = listOf("Ukuran M", "Ukuran L", "Ukuran XL", "Ukuran XXL"),
            isPopular = true,
            isRecommended = false
        ),
        ProductDto(
            id = "prod-4",
            name = "Kemeja Casual Linen Breathable",
            description = "Kemeja kasual pria dan wanita dari bahan rami linen premium. Sangat sejuk di cuaca tropis, tidak mudah kusut, dan cocok untuk santai maupun semi-formal.",
            price = 145000,
            originalPrice = 195000,
            discountPercent = 25,
            category = "Fashion",
            imageUrl = "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&auto=format&fit=crop&q=80",
            rating = 4.8,
            soldCount = 310,
            stock = 35,
            variants = listOf("Broken White - M", "Broken White - L", "Sage Green - M", "Sage Green - L"),
            isPopular = false,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-5",
            name = "Sneakers Running Ultra Light Flyknit",
            description = "Sepatu lari sol busa EVA empuk fleksibel dengan upper rajut bernapas. Ringan hanya 220 gram per sepatu, anti slip, dan awet untuk aktivitas harian.",
            price = 289000,
            originalPrice = 420000,
            discountPercent = 31,
            category = "Sepatu",
            imageUrl = "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&auto=format&fit=crop&q=80",
            rating = 4.9,
            soldCount = 940,
            stock = 28,
            variants = listOf("Size 39", "Size 40", "Size 41", "Size 42", "Size 43"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-6",
            name = "Sepatu Loafers Kulit Classic Urban",
            description = "Sepatu slip-on kasual berbahan kulit microfiber halus tahan gores. Insole busa empuk memberikan kenyamanan ekstra saat dipakai seharian di kantor maupun hangout.",
            price = 225000,
            originalPrice = 310000,
            discountPercent = 27,
            category = "Sepatu",
            imageUrl = "https://images.unsplash.com/photo-1533867617858-e7b97e060509?w=600&auto=format&fit=crop&q=80",
            rating = 4.6,
            soldCount = 180,
            stock = 15,
            variants = listOf("Hitam - 40", "Hitam - 41", "Cokelat - 40", "Cokelat - 41"),
            isPopular = false,
            isRecommended = false
        ),
        ProductDto(
            id = "prod-7",
            name = "Kacamata Hitam Polarized UV400",
            description = "Kacamata pelindung silau dengan lensa polaroid UV400 perlindungan mata optimal. Frame polikarbonat kuat nan ringan dengan desain aviator elegan.",
            price = 85000,
            originalPrice = 135000,
            discountPercent = 37,
            category = "Aksesori",
            imageUrl = "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&auto=format&fit=crop&q=80",
            rating = 4.8,
            soldCount = 650,
            stock = 80,
            variants = listOf("Black Gold", "All Matte Black", "Silver Mirror"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-8",
            name = "Tas Ransel Laptop Waterproof Urban Minimalist",
            description = "Backpack laptop tahan air berkapasitas 20 liter, kompartemen busa empuk untuk laptop hingga 15.6 inci, port pengisian daya USB eksternal, dan tali bahu ergonomis.",
            price = 175000,
            originalPrice = 260000,
            discountPercent = 32,
            category = "Aksesori",
            imageUrl = "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=600&auto=format&fit=crop&q=80",
            rating = 4.9,
            soldCount = 1100,
            stock = 45,
            variants = listOf("Charcoal Gray", "Midnight Navy", "Solid Black"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-9",
            name = "Buku Seni Menguasai Kebiasaan Positif",
            description = "Buku motivasi dan pengembangan diri terlaris karya pakar perilaku. Membahas strategi ilmiah langkah demi langkah membangun kebiasaan produktif dan menghilangkan distraksi.",
            price = 88000,
            originalPrice = 110000,
            discountPercent = 20,
            category = "Buku",
            imageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop&q=80",
            rating = 4.9,
            soldCount = 2300,
            stock = 100,
            variants = listOf("Softcover", "Hardcover Eksklusif"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-10",
            name = "Buku Dasar Pemrograman Android & Kotlin",
            description = "Panduan komprehensif bagi mahasiswa dan pengembang pemula dalam membuat aplikasi mobile modern dengan Kotlin, Jetpack, ViewModel, Room, dan Retrofit.",
            price = 95000,
            originalPrice = 125000,
            discountPercent = 24,
            category = "Buku",
            imageUrl = "https://images.unsplash.com/photo-1532012164546-f432f2e37b73?w=600&auto=format&fit=crop&q=80",
            rating = 5.0,
            soldCount = 540,
            stock = 30,
            variants = listOf("Edisi Mahasiswa 2026", "Paket Buku + Cheatsheet"),
            isPopular = false,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-11",
            name = "Kopi Arabika Gayo Specialty Roast 250g",
            description = "Biji kopi sangrai arabika Gayo single origin grade 1 dengan tasting note citrus, brown sugar, dan lingering chocolate finish. Fresh roasted setiap minggu.",
            price = 68000,
            originalPrice = 85000,
            discountPercent = 20,
            category = "Makanan",
            imageUrl = "https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=600&auto=format&fit=crop&q=80",
            rating = 4.9,
            soldCount = 1450,
            stock = 75,
            variants = listOf("Biji Utuh (Whole Bean)", "Giling Halus (Espresso)", "Giling Sedang (V60)"),
            isPopular = true,
            isRecommended = true
        ),
        ProductDto(
            id = "prod-12",
            name = "Artisan Cookies Dark Choco Cashew 200g",
            description = "Kue kering renyah dengan lelehan cokelat hitam premium 70% dan taburan kacang mede panggang gurih. Dibuat dengan mentega New Zealand tanpa bahan pengawet.",
            price = 45000,
            originalPrice = 60000,
            discountPercent = 25,
            category = "Makanan",
            imageUrl = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=600&auto=format&fit=crop&q=80",
            rating = 4.8,
            soldCount = 780,
            stock = 50,
            variants = listOf("Original Dark Choco", "Double Choco Melt", "Salted Caramel"),
            isPopular = false,
            isRecommended = true
        )
    )

    // Keranjang in-memory
    private val cartItems = mutableListOf<CartItemDto>()

    // Pesanan in-memory
    private val orders = mutableListOf<OrderDto>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (ApiConfig.activeMode != BackendMode.MOCK_SANDBOX) {
            return chain.proceed(request)
        }

        // Simulasi latensi jaringan (100 - 250ms)
        try {
            Thread.sleep(150)
        } catch (_: InterruptedException) { }

        val url = request.url
        val path = url.encodedPath
        val method = request.method

        // 1. GET /api/products
        if (path == "/api/products" && method == "GET") {
            val search = url.queryParameter("search")
            val category = url.queryParameter("category")

            var filtered = productCatalog.toList()
            if (!search.isNullOrBlank()) {
                filtered = filtered.filter {
                    it.name.contains(search, ignoreCase = true) ||
                            it.description.contains(search, ignoreCase = true) ||
                            it.category.contains(search, ignoreCase = true)
                }
            }
            if (!category.isNullOrBlank() && category != "Semua") {
                filtered = filtered.filter { it.category.equals(category, ignoreCase = true) }
            }

            return createJsonResponse(request, 200, gson.toJson(filtered))
        }

        // 2. GET /api/products/{id}
        if (path.startsWith("/api/products/") && method == "GET") {
            val id = path.removePrefix("/api/products/")
            val product = productCatalog.find { it.id == id }
            return if (product != null) {
                createJsonResponse(request, 200, gson.toJson(product))
            } else {
                createJsonResponse(request, 404, "{\"error\": \"Produk tidak ditemukan\"}")
            }
        }

        // 3. GET /api/cart
        if (path == "/api/cart" && method == "GET") {
            return createJsonResponse(request, 200, gson.toJson(cartItems))
        }

        // 4. POST /api/cart
        if (path == "/api/cart" && method == "POST") {
            val bodyString = requestBodyToString(request)
            val addRequest = gson.fromJson(bodyString, AddToCartRequest::class.java)
            val product = productCatalog.find { it.id == addRequest.productId }
                ?: return createJsonResponse(request, 404, "{\"error\": \"Produk tidak ditemukan\"}")

            val existing = cartItems.find { it.productId == addRequest.productId && it.variant == addRequest.variant }
            val resultItem = if (existing != null) {
                existing.quantity += addRequest.quantity
                existing
            } else {
                val newItem = CartItemDto(
                    id = "cart-${UUID.randomUUID().toString().take(8)}",
                    productId = product.id,
                    productName = product.name,
                    productPrice = product.price,
                    productImage = product.imageUrl,
                    variant = addRequest.variant,
                    quantity = addRequest.quantity,
                    stock = product.stock,
                    isSelected = true
                )
                cartItems.add(newItem)
                newItem
            }

            return createJsonResponse(request, 201, gson.toJson(resultItem))
        }

        // 5. PUT /api/cart/{id}
        if (path.startsWith("/api/cart/") && method == "PUT") {
            val id = path.removePrefix("/api/cart/")
            val bodyString = requestBodyToString(request)
            val updateReq = gson.fromJson(bodyString, UpdateCartQuantityRequest::class.java)

            val item = cartItems.find { it.id == id }
            return if (item != null) {
                item.quantity = updateReq.quantity.coerceAtLeast(1)
                createJsonResponse(request, 200, gson.toJson(item))
            } else {
                createJsonResponse(request, 404, "{\"error\": \"Item keranjang tidak ditemukan\"}")
            }
        }

        // 6. DELETE /api/cart/{id}
        if (path.startsWith("/api/cart/") && method == "DELETE") {
            val id = path.removePrefix("/api/cart/")
            val removed = cartItems.removeAll { it.id == id }
            return if (removed) {
                createJsonResponse(request, 200, gson.toJson(DeleteResponse(true, "Item berhasil dihapus")))
            } else {
                createJsonResponse(request, 404, "{\"error\": \"Item tidak ditemukan\"}")
            }
        }

        // 7. POST /api/checkout
        if (path == "/api/checkout" && method == "POST") {
            val bodyString = requestBodyToString(request)
            val checkoutReq = gson.fromJson(bodyString, CheckoutRequest::class.java)

            val subtotal = checkoutReq.items.sumOf { it.productPrice * it.quantity }
            val total = subtotal + checkoutReq.shippingFee
            val dateStr = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
            val orderNumber = "SHP-$dateStr-${(1000..9999).random()}"

            val order = OrderDto(
                id = "order-${UUID.randomUUID().toString().take(8)}",
                orderNumber = orderNumber,
                items = checkoutReq.items,
                subtotal = subtotal,
                shippingFee = checkoutReq.shippingFee,
                totalAmount = total,
                status = "PENDING",
                paymentMethod = checkoutReq.paymentMethod,
                vaNumber = "123 456 7890",
                shippingAddress = checkoutReq.shippingAddress,
                recipientName = checkoutReq.recipientName,
                recipientPhone = checkoutReq.recipientPhone,
                createdAt = System.currentTimeMillis()
            )

            orders.add(0, order)

            // Hapus barang yang telah di-checkout dari cart
            val purchasedIds = checkoutReq.items.map { it.id }.toSet()
            cartItems.removeAll { purchasedIds.contains(it.id) }

            return createJsonResponse(request, 201, gson.toJson(order))
        }

        // 8. POST /api/payment/simulate
        if (path == "/api/payment/simulate" && method == "POST") {
            val orderId = url.queryParameter("order_id") ?: ""
            val status = url.queryParameter("status") ?: "PAID"

            val order = orders.find { it.id == orderId || it.orderNumber == orderId }
            return if (order != null) {
                val index = orders.indexOf(order)
                val updated = order.copy(status = status)
                orders[index] = updated
                val responseObj = PaymentResponse(
                    success = true,
                    orderId = updated.id,
                    status = updated.status,
                    message = "Simulasi pembayaran status: $status berhasil diperbarui"
                )
                createJsonResponse(request, 200, gson.toJson(responseObj))
            } else {
                createJsonResponse(request, 404, "{\"error\": \"Pesanan tidak ditemukan\"}")
            }
        }

        // 9. GET /api/orders
        if (path == "/api/orders" && method == "GET") {
            return createJsonResponse(request, 200, gson.toJson(orders))
        }

        // 10. GET /api/orders/{id}
        if (path.startsWith("/api/orders/") && method == "GET") {
            val id = path.removePrefix("/api/orders/")
            val order = orders.find { it.id == id || it.orderNumber == id }
            return if (order != null) {
                createJsonResponse(request, 200, gson.toJson(order))
            } else {
                createJsonResponse(request, 404, "{\"error\": \"Pesanan tidak ditemukan\"}")
            }
        }

        // Fallback jika tidak matched
        return chain.proceed(request)
    }

    private fun requestBodyToString(request: okhttp3.Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body?.writeTo(buffer)
            buffer.readString(StandardCharsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
    }

    private fun createJsonResponse(request: okhttp3.Request, code: Int, json: String): Response {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = json.toResponseBody(mediaType)
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message(if (code in 200..299) "OK" else "Error")
            .body(body)
            .build()
    }
}
