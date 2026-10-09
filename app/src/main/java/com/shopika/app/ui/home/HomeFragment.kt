package com.shopika.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopika.app.data.model.ProductDto
import com.shopika.app.databinding.FragmentHomeBinding
import com.shopika.app.ui.adapter.CategoryAdapter
import com.shopika.app.ui.adapter.ProductAdapter
import com.shopika.app.ui.cart.CartActivity
import com.shopika.app.ui.product.ProductDetailActivity
import com.shopika.app.ui.product.ProductListActivity
import com.shopika.app.ui.search.SearchActivity
import com.shopika.app.ui.viewmodel.CartViewModel
import com.shopika.app.ui.viewmodel.ProductViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel
import com.shopika.app.util.Resource

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var wishlistViewModel: WishlistViewModel

    private lateinit var popularAdapter: ProductAdapter
    private lateinit var recommendedAdapter: ProductAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        productViewModel = ViewModelProvider(requireActivity(), factory)[ProductViewModel::class.java]
        cartViewModel = ViewModelProvider(requireActivity(), factory)[CartViewModel::class.java]
        wishlistViewModel = ViewModelProvider(requireActivity(), factory)[WishlistViewModel::class.java]

        setupRecyclerViews()
        setupListeners()
        observeData()

        productViewModel.loadProducts()
        cartViewModel.fetchCart()
        wishlistViewModel.loadFavorites()
    }

    private fun setupRecyclerViews() {
        // 1. Categories
        categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            val intent = Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(ProductListActivity.EXTRA_CATEGORY, selectedCategory)
            }
            startActivity(intent)
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // 2. Popular Products
        popularAdapter = ProductAdapter(
            onItemClick = { product -> openProductDetail(product) },
            onFavoriteClick = { product -> wishlistViewModel.toggleFavorite(product.id) }
        )
        binding.rvPopularProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }

        // 3. Recommended Products (2 Kolom)
        recommendedAdapter = ProductAdapter(
            onItemClick = { product -> openProductDetail(product) },
            onFavoriteClick = { product -> wishlistViewModel.toggleFavorite(product.id) }
        )
        binding.rvRecommendedProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = recommendedAdapter
        }
    }

    private fun setupListeners() {
        // Klik search bar membuka SearchActivity
        binding.searchBarContainer.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }

        // Klik cart di header membuka CartActivity
        binding.btnHeaderCart.setOnClickListener {
            startActivity(Intent(requireContext(), CartActivity::class.java))
        }

        // Tombol Lihat Semua
        binding.tvSeeAllPopular.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java))
        }

        // Retry button jika error
        binding.btnRetry.setOnClickListener {
            productViewModel.loadProducts()
        }
    }

    private fun observeData() {
        // Observasi Produk dari API
        productViewModel.products.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutError.visibility = View.GONE
                    val allProducts = resource.data
                    val popular = allProducts.filter { it.isPopular }
                    val recommended = allProducts.filter { it.isRecommended }

                    popularAdapter.submitList(if (popular.isNotEmpty()) popular else allProducts.take(4))
                    recommendedAdapter.submitList(if (recommended.isNotEmpty()) recommended else allProducts)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutError.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = resource.message
                }
            }
        }

        // Observasi ID Favorit untuk sinkronisasi ikon hati
        wishlistViewModel.favoriteIds.observe(viewLifecycleOwner) { ids ->
            val setIds = ids.toSet()
            popularAdapter.setFavoriteIds(setIds)
            recommendedAdapter.setFavoriteIds(setIds)
        }

        // Observasi badge keranjang di header
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            val count = items.sumOf { it.quantity }
            if (count > 0) {
                binding.tvCartBadge.visibility = View.VISIBLE
                binding.tvCartBadge.text = count.toString()
            } else {
                binding.tvCartBadge.visibility = View.GONE
            }
        }
    }

    private fun openProductDetail(product: ProductDto) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
