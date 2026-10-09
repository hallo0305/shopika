package com.shopika.app.ui.wishlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shopika.app.databinding.FragmentWishlistBinding
import com.shopika.app.ui.adapter.ProductAdapter
import com.shopika.app.ui.product.ProductDetailActivity
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel

class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        wishlistViewModel = ViewModelProvider(requireActivity(), factory)[WishlistViewModel::class.java]

        setupRecyclerView()
        observeData()

        wishlistViewModel.loadFavorites()
    }

    private fun setupRecyclerView() {
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

        binding.rvWishlist.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    private fun observeData() {
        wishlistViewModel.favoriteProducts.observe(viewLifecycleOwner) { products ->
            if (products.isEmpty()) {
                binding.layoutEmptyWishlist.visibility = View.VISIBLE
                binding.rvWishlist.visibility = View.GONE
                productAdapter.submitList(emptyList())
            } else {
                binding.layoutEmptyWishlist.visibility = View.GONE
                binding.rvWishlist.visibility = View.VISIBLE
                productAdapter.submitList(products)
            }
        }

        wishlistViewModel.favoriteIds.observe(viewLifecycleOwner) { ids ->
            productAdapter.setFavoriteIds(ids.toSet())
        }
    }

    override fun onResume() {
        super.onResume()
        wishlistViewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
