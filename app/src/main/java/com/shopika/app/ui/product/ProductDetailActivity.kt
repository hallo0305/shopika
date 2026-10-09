package com.shopika.app.ui.product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.shopika.app.R
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.model.ProductDto
import com.shopika.app.databinding.ActivityProductDetailBinding
import com.shopika.app.ui.cart.CartActivity
import com.shopika.app.ui.checkout.CheckoutActivity
import com.shopika.app.ui.viewmodel.CartViewModel
import com.shopika.app.ui.viewmodel.ProductViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.viewmodel.WishlistViewModel
import com.shopika.app.util.Resource
import com.shopika.app.util.RupiahUtils
import java.util.UUID

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var wishlistViewModel: WishlistViewModel

    private var currentProduct: ProductDto? = null
    private var selectedVariant: String = ""
    private var quantity: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID) ?: ""
        if (productId.isEmpty()) {
            Toast.makeText(this, "Produk tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val factory = ViewModelFactory.getInstance(this)
        productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]
        wishlistViewModel = ViewModelProvider(this, factory)[WishlistViewModel::class.java]

        setupListeners()
        observeData()

        productViewModel.loadProductDetail(productId)
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.btnFavorite.setOnClickListener {
            currentProduct?.let { prod ->
                wishlistViewModel.toggleFavorite(prod.id)
            }
        }

        // Stepper Quantity
        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
            }
        }

        binding.btnPlus.setOnClickListener {
            val maxStock = currentProduct?.stock ?: 99
            if (quantity < maxStock) {
                quantity++
                binding.tvQuantity.text = quantity.toString()
            } else {
                Toast.makeText(this, "Batas stok maksimal tercapai ($maxStock)", Toast.LENGTH_SHORT).show()
            }
        }

        // Tambah ke Keranjang
        binding.btnAddToCart.setOnClickListener {
            val product = currentProduct ?: return@setOnClickListener
            val variant = if (selectedVariant.isNotEmpty()) selectedVariant else product.variants.firstOrNull() ?: "Default"
            isBuyNowAction = false
            cartViewModel.addToCart(product.id, variant, quantity)
        }

        // Beli Sekarang -> Masuk ke Checkout
        binding.btnBuyNow.setOnClickListener {
            val product = currentProduct ?: return@setOnClickListener
            val variant = if (selectedVariant.isNotEmpty()) selectedVariant else product.variants.firstOrNull() ?: "Default"
            isBuyNowAction = true
            cartViewModel.addToCart(product.id, variant, quantity)
        }
    }

    private var isBuyNowAction = false

    private fun observeData() {
        // Observasi status penambahan ke keranjang dari backend/Room
        cartViewModel.cartOperationStatus.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.btnAddToCart.isEnabled = false
                    binding.btnBuyNow.isEnabled = false
                }
                is Resource.Success -> {
                    binding.btnAddToCart.isEnabled = true
                    binding.btnBuyNow.isEnabled = true
                    val currentName = currentProduct?.name ?: "Produk"
                    if (isBuyNowAction) {
                        isBuyNowAction = false
                        val intent = Intent(this, CheckoutActivity::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(
                            binding.root,
                            "✓ $currentName berhasil ditambahkan!",
                            Snackbar.LENGTH_LONG
                        ).setAction("Lihat") {
                            startActivity(Intent(this, CartActivity::class.java))
                        }.show()
                    }
                }
                is Resource.Error -> {
                    binding.btnAddToCart.isEnabled = true
                    binding.btnBuyNow.isEnabled = true
                    isBuyNowAction = false
                    val snack = Snackbar.make(
                        binding.root,
                        "✕ Gagal: ${resource.message}",
                        Snackbar.LENGTH_LONG
                    )
                    snack.view.setBackgroundColor(ContextCompat.getColor(this, R.color.error))
                    snack.show()
                }
            }
        }

        productViewModel.productDetail.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    bindProduct(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        wishlistViewModel.favoriteIds.observe(this) { ids ->
            val isFav = currentProduct?.let { ids.contains(it.id) } ?: false
            if (isFav) {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                binding.btnFavorite.setColorFilter(ContextCompat.getColor(this, R.color.primary))
            } else {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                binding.btnFavorite.setColorFilter(ContextCompat.getColor(this, R.color.text_primary))
            }
        }
    }

    private fun bindProduct(product: ProductDto) {
        currentProduct = product

        binding.tvProductName.text = product.name
        binding.tvPrice.text = RupiahUtils.format(product.price)
        binding.tvRating.text = String.format("%.1f", product.rating)
        binding.tvSoldCount.text = "Terjual ${product.soldCount}"
        binding.tvStock.text = "Stok: ${product.stock}"
        binding.tvDescription.text = product.description

        if (product.discountPercent > 0 && product.originalPrice != null) {
            binding.tvDiscountBadge.visibility = View.VISIBLE
            binding.tvDiscountBadge.text = "-${product.discountPercent}%"
            binding.tvOriginalPrice.visibility = View.VISIBLE
            binding.tvOriginalPrice.text = RupiahUtils.format(product.originalPrice)
            binding.tvOriginalPrice.paintFlags = binding.tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            binding.tvDiscountBadge.visibility = View.GONE
            binding.tvOriginalPrice.visibility = View.GONE
        }

        binding.ivProductDetail.load(product.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_shopika_logo)
            error(R.drawable.ic_shopika_logo)
        }

        // Render Variant Chips
        binding.chipGroupVariants.removeAllViews()
        val variants = if (product.variants.isNotEmpty()) product.variants else listOf("Standar")
        selectedVariant = variants.first()

        variants.forEachIndexed { index, variantName ->
            val chip = Chip(this).apply {
                text = variantName
                isCheckable = true
                isChecked = index == 0
                setChipBackgroundColorResource(R.color.chip_bg)
                setTextColor(ContextCompat.getColor(context, R.color.text_primary))
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedVariant = variantName
                    }
                }
            }
            binding.chipGroupVariants.addView(chip)
        }
    }

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }
}
