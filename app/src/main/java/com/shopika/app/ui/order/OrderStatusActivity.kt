package com.shopika.app.ui.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.shopika.app.R
import com.shopika.app.databinding.ActivityOrderStatusBinding
import com.shopika.app.ui.main.MainActivity
import com.shopika.app.ui.viewmodel.OrderViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.util.Resource
import com.shopika.app.util.RupiahUtils

class OrderStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderStatusBinding
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getStringExtra(EXTRA_ORDER_ID) ?: ""
        val statusHint = intent.getStringExtra(EXTRA_STATUS) ?: "PAID"

        val factory = ViewModelFactory.getInstance(this)
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        setupUI(statusHint)
        setupListeners()

        if (orderId.isNotEmpty()) {
            orderViewModel.loadOrderDetail(orderId)
            observeOrderDetail()
        }
    }

    private fun setupUI(status: String) {
        when (status) {
            "PAID" -> {
                binding.ivStatusIcon.setImageResource(R.drawable.ic_check_circle)
                binding.ivStatusIcon.setColorFilter(ContextCompat.getColor(this, R.color.success))
                binding.tvStatusTitle.text = getString(R.string.status_paid)
                binding.tvStatusMessage.text = "Terima kasih! Pembayaranmu telah berhasil diverifikasi. Pesanan sedang dipersiapkan untuk dikirim ke Kota Cirebon."
            }
            "FAILED" -> {
                binding.ivStatusIcon.setImageResource(R.drawable.ic_error_circle)
                binding.ivStatusIcon.setColorFilter(ContextCompat.getColor(this, R.color.error))
                binding.tvStatusTitle.text = getString(R.string.status_failed)
                binding.tvStatusMessage.text = "Waktu pembayaran telah habis atau transaksi dibatalkan."
            }
            else -> {
                binding.ivStatusIcon.setImageResource(R.drawable.ic_clock)
                binding.ivStatusIcon.setColorFilter(ContextCompat.getColor(this, R.color.warning))
                binding.tvStatusTitle.text = getString(R.string.status_pending)
                binding.tvStatusMessage.text = "Menunggu pembayaran via Virtual Account BCA. Silakan selesaikan pembayaran sebelum batas waktu berakhir."
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { navigateToHome() }

        binding.btnBackToHome.setOnClickListener {
            navigateToHome()
        }

        binding.btnViewOrderHistory.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun observeOrderDetail() {
        orderViewModel.orderDetail.observe(this) { resource ->
            if (resource is Resource.Success) {
                val order = resource.data
                binding.tvOrderNumber.text = order.orderNumber
                binding.tvPaymentMethod.text = order.paymentMethod
                binding.tvTotalAmount.text = RupiahUtils.format(order.totalAmount)
                setupUI(order.status)
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
        const val EXTRA_STATUS = "extra_status"
    }
}
