package com.shopika.app.data.remote

enum class BackendMode(val displayName: String, val description: String) {
    MOCK_SANDBOX(
        displayName = "Mock Sandbox (Demo UAS)",
        description = "Simulasi REST API lokal di level OkHttp dengan latensi jaringan realistis (HTTP 200/201/204). Siap dijalankan tanpa dependensi cloud."
    ),
    SUPABASE_CLOUD(
        displayName = "Supabase Cloud Database (REST)",
        description = "Terhubung langsung ke database PostgreSQL & PostgREST cloud Supabase nyata menggunakan URL dan Anon Key."
    )
}
