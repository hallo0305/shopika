package com.shopika.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shopika.app.R
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.databinding.ItemCartProductBinding
import com.shopika.app.util.RupiahUtils

class CartAdapter(
    private val onSelectToggle: (CartItemDto, Boolean) -> Unit,
    private val onQuantityChange: (CartItemDto, Int) -> Unit,
    private val onDeleteClick: (CartItemDto) -> Unit
) : ListAdapter<CartItemDto, CartAdapter.CartViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItemDto) {
            binding.tvProductName.text = item.productName
            binding.tvVariant.text = "Varian: ${item.variant}"
            binding.tvPrice.text = RupiahUtils.format(item.productPrice)
            binding.tvQuantity.text = item.quantity.toString()

            binding.cbSelect.isChecked = item.isSelected
            binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
                onSelectToggle(item, isChecked)
            }

            binding.ivProduct.load(item.productImage) {
                crossfade(true)
                placeholder(R.drawable.ic_shopika_logo)
                error(R.drawable.ic_shopika_logo)
            }

            binding.btnMinus.isEnabled = item.quantity > 1
            binding.btnMinus.setOnClickListener {
                if (item.quantity > 1) {
                    onQuantityChange(item, item.quantity - 1)
                }
            }

            binding.btnPlus.setOnClickListener {
                if (item.quantity < item.stock) {
                    onQuantityChange(item, item.quantity + 1)
                }
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CartItemDto>() {
        override fun areItemsTheSame(oldItem: CartItemDto, newItem: CartItemDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItemDto, newItem: CartItemDto): Boolean {
            return oldItem == newItem
        }
    }
}
