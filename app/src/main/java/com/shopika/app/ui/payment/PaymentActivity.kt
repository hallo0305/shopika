package com.shopika.app.ui.payment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.shopika.app.data.model.OrderDto
import com.shopika.app.databinding.ActivityPaymentBinding
import com.shopika.app.ui.order.OrderStatusActivity
import com.shopika.app.ui.viewmodel.PaymentViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.R
import com.shopika.app.util.Resource
import com.shopika.app.util.RupiahUtils

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var paymentViewModel: PaymentViewModel
    private var orderId: String = ""
    private var currentOrder: OrderDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderId = intent.getStringExtra(EXTRA_ORDER_ID) ?: ""
        if (orderId.isEmpty()) {
            Toast.makeText(this, "Order ID tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val factory = ViewModelFactory.getInstance(this)
        paymentViewModel = ViewModelProvider(this, factory)[PaymentViewModel::class.java]

        setupListeners()
        observeData()

        paymentViewModel.loadOrder(orderId)
        paymentViewModel.startCountdown()
    }

    private var isManualCheck = false

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        // Tombol Salin Nomor VA
        binding.btnCopyVa.setOnClickListener {
            val vaNumber = binding.tvVaNumber.text.toString().trim()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Shopika VA Number", vaNumber)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Nomor Virtual Account disalin ke clipboard", Toast.LENGTH_SHORT).show()
        }

        // Tombol Simulasikan Pembayaran Berhasil
        binding.btnSimulatePaySuccess.setOnClickListener {
            paymentViewModel.simulatePaymentSuccess(orderId)
        }

        // Tombol Cek Status Pembayaran
        binding.btnCheckPaymentStatus.setOnClickListener {
            isManualCheck = true
            binding.btnCheckPaymentStatus.text = "Memeriksa..."
            paymentViewModel.checkPaymentStatus(orderId)
        }
    }

    private fun observeData() {
        // Observasi Countdown Timer
        paymentViewModel.formattedTimeRemaining.observe(this) { timeString ->
            binding.tvCountdownTimer.text = timeString
        }

        // Observasi Data Order
        paymentViewModel.order.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    binding.btnCheckPaymentStatus.text = getString(R.string.btn_check_payment_status)
                    val order = resource.data
                    currentOrder = order
                    binding.tvOrderNumber.text = "Nomor Pesanan: ${order.orderNumber}"
                    binding.tvPaymentTotal.text = RupiahUtils.format(order.totalAmount)
                    binding.tvVaNumber.text = order.vaNumber

                    // Jika status sudah lunas (PAID), langsung arahkan ke OrderStatusActivity
                    if (order.status == "PAID") {
                        navigateToStatus(order.id, "PAID")
                    } else if (isManualCheck) {
                        isManualCheck = false
                        Toast.makeText(this, "Status saat ini: Menunggu Pembayaran. Silakan selesaikan pembayaran simulasi.", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Error -> {
                    binding.btnCheckPaymentStatus.text = getString(R.string.btn_check_payment_status)
                    isManualCheck = false
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observasi Hasil Simulasi Pembayaran
        paymentViewModel.paymentResult.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.btnSimulatePaySuccess.isEnabled = false
                    binding.btnSimulatePaySuccess.text = "Menghubungi Server..."
                }
                is Resource.Success -> {
                    binding.btnSimulatePaySuccess.isEnabled = true
                    binding.btnSimulatePaySuccess.text = "Simulasikan Bayar Berhasil"
                    Toast.makeText(this, "✓ Pembayaran Berhasil Diverifikasi!", Toast.LENGTH_SHORT).show()
                    navigateToStatus(orderId, "PAID")
                }
                is Resource.Error -> {
                    binding.btnSimulatePaySuccess.isEnabled = true
                    binding.btnSimulatePaySuccess.text = "Simulasikan Bayar Berhasil"
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun navigateToStatus(id: String, status: String) {
        val intent = Intent(this, OrderStatusActivity::class.java).apply {
            putExtra(OrderStatusActivity.EXTRA_ORDER_ID, id)
            putExtra(OrderStatusActivity.EXTRA_STATUS, status)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_ORDER_ID = "extra_order_id"
    }
}
