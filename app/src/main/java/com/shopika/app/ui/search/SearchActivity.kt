package com.shopika.app.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopika.app.databinding.ActivitySearchBinding
import com.shopika.app.ui.adapter.CategoryAdapter
import com.shopika.app.ui.adapter.ProductAdapter
import com.shopika.app.ui.cart.CartActivity
import com.shopika.app.ui.product.ProductDetailActivity
import com.shopika.app.ui.viewmodel.ProductViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel
import com.shopika.app.util.Resource

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    private val categories = listOf(
        "Semua",
        "Elektronik",
        "Fashion",
        "Sepatu",
        "Aksesori",
        "Buku",
        "Makanan"
    )

    private var selectedCategory: String = "Semua"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        wishlistViewModel = ViewModelProvider(this, factory)[WishlistViewModel::class.java]

        setupRecyclerViews()
        setupListeners()
        observeData()

        // Muat semua produk awal
        productViewModel.searchProducts("")
    }

    private fun setupRecyclerViews() {
        categoryAdapter = CategoryAdapter(categories) { cat ->
            selectedCategory = cat
            val query = binding.etSearch.text.toString().trim()
            productViewModel.searchProducts(query, if (cat == "Semua") null else cat)
        }
        binding.rvSearchCategories.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        productAdapter = ProductAdapter(
            onItemClick = { product ->
                val intent = Intent(this, ProductDetailActivity::class.java).apply {
                    putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
                }
                startActivity(intent)
            },
            onFavoriteClick = { product ->
                wishlistViewModel.toggleFavorite(product.id)
            }
        )
        binding.rvSearchResults.apply {
            layoutManager = GridLayoutManager(this@SearchActivity, 2)
            adapter = productAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.btnClearSearch.setOnClickListener {
            binding.etSearch.setText("")
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                binding.btnClearSearch.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
                productViewModel.searchProducts(
                    query,
                    if (selectedCategory == "Semua") null else selectedCategory
                )
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.etSearch.text.toString().trim()
                productViewModel.searchProducts(
                    query,
                    if (selectedCategory == "Semua") null else selectedCategory
                )
                true
            } else {
                false
            }
        }
    }

    private fun observeData() {
        productViewModel.searchResults.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutEmptySearch.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val list = resource.data
                    if (list.isEmpty()) {
                        binding.layoutEmptySearch.visibility = View.VISIBLE
                        productAdapter.submitList(emptyList())
                    } else {
                        binding.layoutEmptySearch.visibility = View.GONE
                        productAdapter.submitList(list)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutEmptySearch.visibility = View.VISIBLE
                    productAdapter.submitList(emptyList())
                }
            }
        }

        wishlistViewModel.favoriteIds.observe(this) { ids ->
            productAdapter.setFavoriteIds(ids.toSet())
        }
    }
}
