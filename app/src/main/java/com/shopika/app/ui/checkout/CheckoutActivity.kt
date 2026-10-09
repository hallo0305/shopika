package com.shopika.app.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.databinding.ActivityCheckoutBinding
import com.shopika.app.ui.adapter.OrderItemAdapter
import com.shopika.app.ui.payment.PaymentActivity
import com.shopika.app.ui.viewmodel.CartViewModel
import com.shopika.app.ui.viewmodel.CheckoutViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.util.Resource
import com.shopika.app.util.RupiahUtils

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var orderItemAdapter: OrderItemAdapter

    private var checkoutItems: List<CartItemDto> = emptyList()
    private var subtotal: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        checkoutViewModel = ViewModelProvider(this, factory)[CheckoutViewModel::class.java]
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        setupListeners()
        observeData()

        cartViewModel.fetchCart()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        // Ubah Alamat Dialog
        binding.btnChangeAddress.setOnClickListener {
            val input = EditText(this).apply {
                setText(checkoutViewModel.shippingAddress.value)
                setSelection(text.length)
            }
            MaterialAlertDialogBuilder(this)
                .setTitle("Ubah Alamat Pengiriman")
                .setView(input)
                .setPositiveButton("Simpan") { _, _ ->
                    val newAddress = input.text.toString().trim()
                    if (newAddress.isNotEmpty()) {
                        checkoutViewModel.shippingAddress.value = newAddress
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        // Radio Button Pilihan Kurir Pengiriman
        binding.rgShippingMethod.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbShippingRegular.id -> checkoutViewModel.selectShippingMethod(isExpress = false)
                binding.rbShippingExpress.id -> checkoutViewModel.selectShippingMethod(isExpress = true)
            }
        }

        // Tombol Buat Pesanan
        binding.btnCreateOrder.setOnClickListener {
            if (checkoutItems.isEmpty()) {
                Toast.makeText(this, "Tidak ada produk untuk dicheckout", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notes = binding.etOrderNotes.text.toString().trim()
            checkoutViewModel.submitCheckout(checkoutItems, notes)
        }
    }

    private fun observeData() {
        // Observasi alamat
        checkoutViewModel.shippingAddress.observe(this) { address ->
            binding.tvShippingAddress.text = address
        }

        // Observasi Cart Items
        cartViewModel.cartItems.observe(this) { items ->
            val selected = items.filter { it.isSelected }
            if (selected.isNotEmpty()) {
                checkoutItems = selected
                subtotal = selected.sumOf { it.productPrice * it.quantity }

                orderItemAdapter = OrderItemAdapter(selected)
                binding.rvCheckoutItems.apply {
                    layoutManager = LinearLayoutManager(this@CheckoutActivity)
                    adapter = orderItemAdapter
                }

                updatePriceSummary()
            }
        }

        // Observasi Ongkir
        checkoutViewModel.shippingFee.observe(this) {
            updatePriceSummary()
        }

        // Observasi Hasil Checkout
        checkoutViewModel.checkoutResult.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.btnCreateOrder.isEnabled = false
                    binding.btnCreateOrder.text = "Memproses Pesanan..."
                }
                is Resource.Success -> {
                    binding.btnCreateOrder.isEnabled = true
                    binding.btnCreateOrder.text = "Buat Pesanan"
                    val order = resource.data
                    Toast.makeText(this, "Pesanan #${order.orderNumber} berhasil dibuat!", Toast.LENGTH_SHORT).show()

                    // Navigasi ke Layar Pembayaran
                    val intent = Intent(this, PaymentActivity::class.java).apply {
                        putExtra(PaymentActivity.EXTRA_ORDER_ID, order.id)
                    }
                    startActivity(intent)
                    finish()
                }
                is Resource.Error -> {
                    binding.btnCreateOrder.isEnabled = true
                    binding.btnCreateOrder.text = "Buat Pesanan"
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updatePriceSummary() {
        val shipping = checkoutViewModel.shippingFee.value ?: 12000L
        val total = subtotal + shipping

        binding.tvSubtotalSummary.text = RupiahUtils.format(subtotal)
        binding.tvShippingFeeSummary.text = RupiahUtils.format(shipping)
        binding.tvTotalSummary.text = RupiahUtils.format(total)
        binding.tvTotalBottom.text = RupiahUtils.format(total)
    }
}
