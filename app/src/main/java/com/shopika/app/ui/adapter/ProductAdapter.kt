package com.shopika.app.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shopika.app.R
import com.shopika.app.data.model.ProductDto
import com.shopika.app.databinding.ItemProductCardBinding
import com.shopika.app.util.RupiahUtils

class ProductAdapter(
    private val onItemClick: (ProductDto) -> Unit,
    private val onFavoriteClick: (ProductDto) -> Unit
) : ListAdapter<ProductDto, ProductAdapter.ProductViewHolder>(DiffCallback) {

    private var favoriteIds: Set<String> = emptySet()

    fun setFavoriteIds(ids: Set<String>) {
        this.favoriteIds = ids
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductDto) {
            binding.tvProductName.text = product.name
            binding.tvCategory.text = product.category
            binding.tvProductPrice.text = RupiahUtils.format(product.price)
            binding.tvRating.text = String.format("%.1f", product.rating)
            binding.tvSoldCount.text = "Terjual ${product.soldCount}"

            // Diskon
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

            // Gambar Produk dengan Coil
            binding.ivProduct.load(product.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_shopika_logo)
                error(R.drawable.ic_shopika_logo)
            }

            // Status Favorit
            val isFav = favoriteIds.contains(product.id)
            if (isFav) {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                binding.btnFavorite.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.primary)
                )
            } else {
                binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                binding.btnFavorite.setColorFilter(
                    ContextCompat.getColor(binding.root.context, R.color.text_secondary)
                )
            }

            binding.root.setOnClickListener { onItemClick(product) }
            binding.btnFavorite.setOnClickListener { onFavoriteClick(product) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProductDto>() {
        override fun areItemsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductDto, newItem: ProductDto): Boolean {
            return oldItem == newItem
        }
    }
}
