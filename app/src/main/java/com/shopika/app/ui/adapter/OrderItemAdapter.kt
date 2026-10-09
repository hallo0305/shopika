package com.shopika.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shopika.app.R
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.databinding.ItemCheckoutProductBinding
import com.shopika.app.util.RupiahUtils

class OrderItemAdapter(
    private val items: List<CartItemDto>
) : RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemCheckoutProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class OrderItemViewHolder(private val binding: ItemCheckoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItemDto) {
            binding.tvProductName.text = item.productName
            binding.tvVariant.text = item.variant
            binding.tvPrice.text = RupiahUtils.format(item.productPrice)
            binding.tvQuantity.text = "x${item.quantity}"

            binding.ivProduct.load(item.productImage) {
                crossfade(true)
                placeholder(R.drawable.ic_shopika_logo)
                error(R.drawable.ic_shopika_logo)
            }
        }
    }
}
