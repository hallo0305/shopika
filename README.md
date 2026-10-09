# Shopika - Aplikasi Mobile Marketplace Android & Kotlin

> **Tagline:** "Belanja Mudah, Pilihan Tanpa Batas"  
> **Projek:** UAS Pemrograman Mobile Dasar  
> **Platform:** Android Studio (Kotlin, MVVM, Room Database, Retrofit 2, Coroutines)

---

## 1. Identitas Visual & Branding

Shopika dirancang dengan filosofi visual modern, clean, minimalis, dan elegan:
* **Primary (Coral Orange):** `#FF6B4A`
* **Primary Dark:** `#E65336`
* **Background:** `#FFF7EF`
* **Surface/Card:** `#FFFFFF`
* **Text Primary:** `#292929`
* **Text Secondary:** `#777777`
* **Border & Divider:** `#F0E5DC`
* **Success:** `#269B69`
* **Error:** `#D64545`

Logo Shopika berupa ikon tas belanja minimalis dengan aksen coral orange dan senyuman kepuasan berbelanja.

---

## 2. 12 Layar Utama Aplikasi

1. **Splash Screen (`SplashActivity`):**
   * Logo Shopika, tagline, dan transisi otomatis ke Beranda.
2. **Beranda (`HomeFragment` di `MainActivity`):**
   * Header logo Shopika dengan notifikasi badge keranjang.
   * Search bar yang interaktif.
   * Banner promosi cashback & gratis ongkir.
   * Kategori produk horizontal.
   * Bagian Produk Terpopuler & Rekomendasi Untukmu dalam grid 2 kolom.
   * Bottom Navigation bar yang konsisten.
3. **Kategori (`CategoryFragment`):**
   * Filter kategori (Elektronik, Fashion, Sepatu, Aksesori, Buku, Makanan).
   * Grid 2 kolom produk terfilter dengan empty state jika kosong.
4. **Daftar Produk (`ProductListActivity`):**
   * Grid katalog produk 2 kolom dengan filter chip kategori dan sorting.
5. **Detail Produk (`ProductDetailActivity`):**
   * Foto produk resolusi tinggi, harga, diskon, rating, dan stok.
   * Pilihan varian interaktif (warna / ukuran) menggunakan Material Chip.
   * Stepper jumlah barang (+ / -) dengan batasan stok.
   * Tombol "Tambah ke Keranjang" (POST) dan "Beli Sekarang".
6. **Pencarian (`SearchActivity`):**
   * Live filter kata kunci dengan text watcher dan tombol clear.
   * Filter cepat berdasarkan kategori.
   * Tampilan visual empty state bila produk tidak ditemukan.
7. **Keranjang (`CartFragment` & `CartActivity`):**
   * Checkbox pilihan per produk & "Pilih Semua".
   * Tombol stepper tambah/kurang jumlah (PUT).
   * Tombol hapus barang (DELETE) dengan konfirmasi.
   * Perhitungan otomatis subtotal dan counter jumlah barang.
   * Tombol Checkout.
8. **Checkout (`CheckoutActivity`):**
   * Alamat pengiriman di Kota Cirebon (*Jl. Pemuda No. 45, Kesambi, Kota Cirebon*).
   * Opsi kurir pengiriman (Reguler Rp12.000 vs Express Rp24.000).
   * Ringkasan produk dan catatan untuk penjual.
   * Perhitungan otomatis total tagihan (subtotal + ongkos kirim).
   * Tombol "Buat Pesanan" (POST).
9. **Pembayaran (`PaymentActivity`):**
   * Metode pembayaran Virtual Account BCA simulasi.
   * Nomor VA contoh: `123 456 7890` dengan tombol salin (Copy).
   * Countdown timer berjalan mundur.
   * Tombol "Simulasikan Bayar Berhasil" (POST).
   * Banner penegasan bahwa ini adalah simulasi sandbox untuk projek UAS.
10. **Status Pesanan (`OrderStatusActivity`):**
    * Status visual (Menunggu Pembayaran, Berhasil, atau Gagal).
    * Nomor pesanan unik (contoh: `SHP-20261009-8472`).
    * Tombol "Kembali ke Beranda" dan "Lihat Riwayat Pesanan".
11. **Favorit (`WishlistFragment`):**
    * Daftar produk yang disukai tersimpan dalam Room Database.
    * Tombol one-tap untuk menghapus favorit atau membuka detail.
12. **Profil (`ProfileFragment`):**
    * Informasi pengguna (*Nuris Kurniawan*, *nuris.kurniawan@student.ac.id*).
    * Counter jumlah pesanan, favorit, dan voucher.
    * Menu riwayat pesanan, alamat Kota Cirebon, metode pembayaran, bantuan/FAQ, serta penggantian tema (Light / Dark mode).

---

## 3. Arsitektur & Teknologi

* **Pola Arsitektur:** MVVM (Model - View - ViewModel) + Repository Pattern.
* **Database Lokal & Cache:** Room Database (`ShopikaDatabase`).
* **Jaringan REST API:** Retrofit 2 + Gson Converter + OkHttp Logging Interceptor.
* **Coroutines & Flow:** Operasi background asynchronous dan reactive state flow.
* **Image Loading:** Coil (Kotlin-first image loader).
* **UI Components:** Material Design 3 (M3) dengan ViewBinding.
* **Backend Cloud & Mock Sandbox:**
  * Endpoint REST API didukung penuh:
    - `GET /api/products` & `GET /api/products?search={q}&category={c}`
    - `GET /api/products/{id}`
    - `GET /api/cart`
    - `POST /api/cart`
    - `PUT /api/cart/{id}`
    - `DELETE /api/cart/{id}`
    - `POST /api/checkout`
    - `POST /api/payment/simulate?order_id={id}&status={status}`
    - `GET /api/orders`
  * Dilengkapi `ShopikaMockApiInterceptor` yang mensimulasikan latensi server realistis dan kode HTTP 200/201/204 sehingga aplikasi siap didemokan langsung saat sidang UAS.
  * Dilengkapi script skema SQL `supabase_schema.sql` untuk kemudahan deploy ke database Supabase.

---

## 4. Cara Menjalankan Aplikasi di Android Studio

1. Buka **Android Studio**.
2. Pilih **Open**, lalu arahkan ke folder `C:\Users\nuris\.gemini\antigravity\scratch\Shopika`.
3. Tunggu proses Gradle Sync selesai secara otomatis.
4. Hubungkan HP Android fisik via USB Debugging atau jalankan Android Emulator.
5. Klik tombol **Run 'app'** (segitiga hijau) atau tekan `Shift + F10`.
