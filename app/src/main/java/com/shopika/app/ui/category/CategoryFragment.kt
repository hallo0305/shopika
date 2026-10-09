package com.shopika.app.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopika.app.databinding.FragmentCategoryBinding
import com.shopika.app.ui.adapter.CategoryAdapter
import com.shopika.app.ui.adapter.ProductAdapter
import com.shopika.app.ui.product.ProductDetailActivity
import com.shopika.app.ui.viewmodel.ProductViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel
import com.shopika.app.util.Resource

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

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

    private var currentCategory: String = "Semua"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        wishlistViewModel = ViewModelProvider(requireActivity(), factory)[WishlistViewModel::class.java]

        setupRecyclerViews()
        observeData()

        productViewModel.loadProducts()
    }

    private fun setupRecyclerViews() {
        categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            currentCategory = selectedCategory
            productViewModel.loadProducts(selectedCategory)
        }
        binding.rvCategoryChips.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        productAdapter = ProductAdapter(
            onItemClick = { product ->
                val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                    putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
                }
                startActivity(intent)
            },
            onFavoriteClick = { product ->
                wishlistViewModel.toggleFavorite(product.id)
            }
        )
        binding.rvCategoryProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    private fun observeData() {
        productViewModel.products.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val items = resource.data
                    if (items.isEmpty()) {
                        binding.layoutEmpty.visibility = View.VISIBLE
                        productAdapter.submitList(emptyList())
                    } else {
                        binding.layoutEmpty.visibility = View.GONE
                        productAdapter.submitList(items)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutEmpty.visibility = View.VISIBLE
                    productAdapter.submitList(emptyList())
                }
            }
        }

        wishlistViewModel.favoriteIds.observe(viewLifecycleOwner) { ids ->
            productAdapter.setFavoriteIds(ids.toSet())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
