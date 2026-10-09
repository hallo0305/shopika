package com.shopika.app.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shopika.app.R
import com.shopika.app.databinding.FragmentCartBinding
import com.shopika.app.ui.adapter.CartAdapter
import com.shopika.app.ui.checkout.CheckoutActivity
import com.shopika.app.ui.main.MainActivity
import com.shopika.app.ui.viewmodel.CartViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.util.RupiahUtils

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        cartViewModel = ViewModelProvider(requireActivity(), factory)[CartViewModel::class.java]

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
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun setupListeners() {
        // Toggle Select All
        binding.cbSelectAll.setOnClickListener {
            val isChecked = binding.cbSelectAll.isChecked
            cartViewModel.toggleSelectAll(isChecked)
        }

        // Start Shopping on Empty State
        binding.btnStartShopping.setOnClickListener {
            (activity as? MainActivity)?.navigateToTab(R.id.menu_home)
        }

        // Checkout Button
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

            val intent = Intent(requireContext(), CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
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

                // Update select all checkbox status
                val allSelected = items.all { it.isSelected }
                binding.cbSelectAll.isChecked = allSelected

                // Hitung Subtotal dan Jumlah Item
                val subtotal = cartViewModel.calculateSubtotal(items)
                val selectedCount = cartViewModel.getSelectedCount(items)

                binding.tvSubtotalAmount.text = RupiahUtils.format(subtotal)
                binding.tvTotalItemsCount.text = "${items.size} jenis barang"
                binding.btnCheckout.text = "Checkout ($selectedCount)"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
