package com.shopika.app.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shopika.app.databinding.ActivityCartBinding
import com.shopika.app.ui.adapter.CartAdapter
import com.shopika.app.ui.checkout.CheckoutActivity
import com.shopika.app.ui.viewmodel.CartViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.util.RupiahUtils

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        setupRecyclerView()
        setupListeners()
        observeData()

        cartViewModel.fetchCart()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onSelectToggle = { item, isChecked ->
                cartViewModel.toggleSelection(item.id, isChecked)
            },
            onQuantityChange = { item, newQty ->
                cartViewModel.updateQuantity(item.id, newQty)
            },
            onDeleteClick = { item ->
                cartViewModel.deleteItem(item.id)
                Snackbar.make(binding.root, "${item.productName} dihapus dari keranjang", Snackbar.LENGTH_SHORT).show()
            }
        )

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.cbSelectAll.setOnClickListener {
            val isChecked = binding.cbSelectAll.isChecked
            cartViewModel.toggleSelectAll(isChecked)
        }

        binding.btnStartShopping.setOnClickListener { finish() }

        binding.btnCheckout.setOnClickListener {
            val items = cartViewModel.cartItems.value.orEmpty().filter { it.isSelected }
            if (items.isEmpty()) {
                Snackbar.make(
                    binding.root,
                    "Pilih minimal satu produk untuk melanjutkan checkout!",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {
        cartViewModel.cartItems.observe(this) { items ->
            if (items.isEmpty()) {
                binding.layoutEmptyCart.visibility = View.VISIBLE
                binding.rvCartItems.visibility = View.GONE
                binding.layoutCheckoutBar.visibility = View.GONE
                binding.layoutSelectAll.visibility = View.GONE
            } else {
                binding.layoutEmptyCart.visibility = View.GONE
                binding.rvCartItems.visibility = View.VISIBLE
                binding.layoutCheckoutBar.visibility = View.VISIBLE
                binding.layoutSelectAll.visibility = View.VISIBLE

                cartAdapter.submitList(items)

                val allSelected = items.all { it.isSelected }
                binding.cbSelectAll.isChecked = allSelected

                val subtotal = cartViewModel.calculateSubtotal(items)
                val selectedCount = cartViewModel.getSelectedCount(items)

                binding.tvSubtotalAmount.text = RupiahUtils.format(subtotal)
                binding.tvTotalItemsCount.text = "${items.size} jenis barang"
                binding.btnCheckout.text = "Checkout ($selectedCount)"
            }
        }
    }
}
