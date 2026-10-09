package com.shopika.app.data.remote

import com.shopika.app.BuildConfig

object ApiConfig {
    /**
     * Mode Backend yang aktif:
     * - BackendMode.MOCK_SANDBOX (Default saat presentasi / demo tanpa server)
     * - BackendMode.SUPABASE_CLOUD (Saat terhubung ke cloud Supabase)
     */
    var activeMode: BackendMode = if (BuildConfig.SUPABASE_URL.isNotBlank() && BuildConfig.SUPABASE_ANON_KEY.isNotBlank()) {
        BackendMode.SUPABASE_CLOUD
    } else {
        BackendMode.MOCK_SANDBOX
    }

    /**
     * Konfigurasi Supabase yang diinjeksi secara aman dari local.properties (BuildConfig).
     * Anon Key adalah public key dengan Row Level Security, bukan secret service role.
     */
    val SUPABASE_URL: String = BuildConfig.SUPABASE_URL
    val SUPABASE_ANON_KEY: String = BuildConfig.SUPABASE_ANON_KEY

    /**
     * Base URL lokal untuk mock router
     */
    const val BASE_URL: String = "https://mock.shopika.local/"

    fun isCloudConfigured(): Boolean {
        return SUPABASE_URL.isNotBlank() && SUPABASE_ANON_KEY.isNotBlank()
    }
}
