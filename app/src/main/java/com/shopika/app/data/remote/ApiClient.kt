package com.shopika.app.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val mockInterceptor = ShopikaMockApiInterceptor()
    private val supabaseInterceptor = SupabaseCloudInterceptor()

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            // Jika mode MOCK_SANDBOX, mockInterceptor merespons langsung dengan data simulasi
            .addInterceptor(mockInterceptor)
            // Jika mode SUPABASE_CLOUD, supabaseInterceptor mentransformasi request ke PostgREST
            .addInterceptor(supabaseInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
