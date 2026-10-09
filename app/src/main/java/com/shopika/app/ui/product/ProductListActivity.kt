package com.shopika.app.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopika.app.databinding.ActivityProductListBinding
import com.shopika.app.ui.adapter.CategoryAdapter
import com.shopika.app.ui.adapter.ProductAdapter
import com.shopika.app.ui.cart.CartActivity
import com.shopika.app.ui.viewmodel.ProductViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel
import com.shopika.app.util.Resource

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
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
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedCategory = intent.getStringExtra(EXTRA_CATEGORY) ?: "Semua"

        val factory = ViewModelFactory.getInstance(this)
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        wishlistViewModel = ViewModelProvider(this, factory)[WishlistViewModel::class.java]

        setupRecyclerViews()
        setupListeners()
        observeData()

        categoryAdapter.setSelectedCategory(selectedCategory)
        productViewModel.loadProducts(selectedCategory)
        wishlistViewModel.loadFavorites()
    }

    private fun setupRecyclerViews() {
        categoryAdapter = CategoryAdapter(categories) { cat ->
            selectedCategory = cat
            binding.tvTitle.text = if (cat == "Semua") "Semua Produk" else "Kategori $cat"
            productViewModel.loadProducts(cat)
        }
        binding.rvCategoryChips.apply {
            layoutManager = LinearLayoutManager(this@ProductListActivity, LinearLayoutManager.HORIZONTAL, false)
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
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(this@ProductListActivity, 2)
            adapter = productAdapter
        }

        binding.tvTitle.text = if (selectedCategory == "Semua") "Semua Produk" else "Kategori $selectedCategory"
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun observeData() {
        productViewModel.products.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val list = resource.data
                    if (list.isEmpty()) {
                        binding.layoutEmpty.visibility = View.VISIBLE
                        productAdapter.submitList(emptyList())
                    } else {
                        binding.layoutEmpty.visibility = View.GONE
                        productAdapter.submitList(list)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutEmpty.visibility = View.VISIBLE
                    productAdapter.submitList(emptyList())
                }
            }
        }

        wishlistViewModel.favoriteIds.observe(this) { ids ->
            productAdapter.setFavoriteIds(ids.toSet())
        }
    }

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }
}
