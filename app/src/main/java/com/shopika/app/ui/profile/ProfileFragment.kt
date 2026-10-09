package com.shopika.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shopika.app.databinding.FragmentProfileBinding
import com.shopika.app.R
import com.shopika.app.ui.main.MainActivity
import com.shopika.app.ui.order.OrderHistoryActivity
import com.shopika.app.ui.viewmodel.OrderViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel
import com.shopika.app.util.Resource

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var wishlistViewModel: WishlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
        wishlistViewModel = ViewModelProvider(requireActivity(), factory)[WishlistViewModel::class.java]

        setupListeners()
        observeStats()

        orderViewModel.loadOrders()
        wishlistViewModel.loadFavorites()
    }

    private fun setupListeners() {
        // Klik card order atau menu order -> OrderHistoryActivity
        binding.cardStatOrders.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }
        binding.menuOrders.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }

        // Klik card favorit -> Tab Favorit
        binding.cardStatFavorites.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.menu_wishlist)
        }

        // Daftar Alamat Dialog
        binding.menuAddress.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Alamat Pengiriman Utama")
                .setMessage("Penerima: Raffi Nur Islami\nTelepon: +62 812-3456-7890\n\nJl. Pemuda No. 45, Sunyaragi, Kec. Kesambi, Kota Cirebon, Jawa Barat 45132")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // Metode Pembayaran Dialog
        binding.menuPayment.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Metode Pembayaran Tersimpan")
                .setMessage("1. Virtual Account BCA (Otomatis & Simulasi Sandbox)\n2. Gopay / QRIS E-Wallet\n3. Bank Transfer Manual")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // Bantuan FAQ
        binding.menuHelp.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Pusat Bantuan & FAQ")
                .setMessage("Shopika v1.0 - Aplikasi E-Commerce Modern Projek UAS Pemrograman Mobile Dasar.\n\n• Bagaimana cara simulasi bayar? Masuk ke Checkout -> Pilih Pembayaran -> Klik tombol 'Simulasikan Bayar Berhasil'.\n• Apakah transaksi nyata? Tidak, ini adalah simulasi sandbox.")
                .setPositiveButton("Mengerti", null)
                .show()
        }

        // Pengaturan Aplikasi
        binding.menuSettings.setOnClickListener {
            val settingsMenu = arrayOf("Ganti Tema (Light / Dark)", "Status Backend & Cloud Supabase")
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Pengaturan Aplikasi")
                .setItems(settingsMenu) { _, index ->
                    when (index) {
                        0 -> showThemeDialog()
                        1 -> showBackendInfoDialog()
                    }
                }
                .setNegativeButton("Tutup", null)
                .show()
        }

        // Keluar Akun
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah kamu yakin ingin keluar dari akun Shopika?")
                .setPositiveButton("Keluar") { _, _ ->
                    Toast.makeText(requireContext(), "Berhasil keluar dari akun", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    private fun observeStats() {
        orderViewModel.orders.observe(viewLifecycleOwner) { resource ->
            if (resource is Resource.Success) {
                binding.tvStatOrderCount.text = resource.data.size.toString()
            }
        }

        wishlistViewModel.favoriteIds.observe(viewLifecycleOwner) { ids ->
            binding.tvStatFavCount.text = ids.size.toString()
        }
    }

    private fun showThemeDialog() {
        val options = arrayOf("Tema Terang (Light)", "Tema Gelap (Dark)", "Mengikuti Sistem")
        val checkedItem = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> 0
            AppCompatDelegate.MODE_NIGHT_YES -> 1
            else -> 2
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pengaturan Tampilan Tema")
            .setSingleChoiceItems(options, checkedItem) { dialog, which ->
                when (which) {
                    0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showBackendInfoDialog() {
        val currentMode = com.shopika.app.data.remote.ApiConfig.activeMode
        val isConfigured = com.shopika.app.data.remote.ApiConfig.isCloudConfigured()

        val infoMessage = StringBuilder()
        infoMessage.append("Mode Aktif: ${currentMode.displayName}\n\n")
        infoMessage.append("${currentMode.description}\n\n")

        if (isConfigured) {
            infoMessage.append("✓ Supabase URL terhubung: ${com.shopika.app.data.remote.ApiConfig.SUPABASE_URL}\n")
            infoMessage.append("✓ Anon Public Key aktif dan aman dengan RLS.")
        } else {
            infoMessage.append("ℹ Supabase Cloud belum dihubungkan.\n")
            infoMessage.append("Untuk menghubungkan ke backend Supabase Anda:\n")
            infoMessage.append("1. Buka file local.properties di root proyek.\n")
            infoMessage.append("2. Tambahkan baris:\n")
            infoMessage.append("   supabase.url=https://xyz.supabase.co\n")
            infoMessage.append("   supabase.anon.key=eyJhbGciOi...\n")
            infoMessage.append("3. Jalankan script supabase_schema.sql di SQL Editor Supabase.")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Arsitektur Backend Shopika")
            .setMessage(infoMessage.toString())
            .setPositiveButton("Mengerti", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
