package com.shopika.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shopika.app.R
import com.shopika.app.data.model.OrderDto
import com.shopika.app.databinding.ItemOrderHistoryBinding
import com.shopika.app.util.RupiahUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderHistoryAdapter(
    private val onItemClick: (OrderDto) -> Unit
) : ListAdapter<OrderDto, OrderHistoryAdapter.OrderViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderDto) {
            val context = binding.root.context
            binding.tvOrderNumber.text = order.orderNumber
            binding.tvTotalAmount.text = RupiahUtils.format(order.totalAmount)

            // Format tanggal
            val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm 'WIB'", Locale.forLanguageTag("id-ID"))
            binding.tvOrderDate.text = dateFormat.format(Date(order.createdAt))

            // First item info
            val firstItem = order.items.firstOrNull()
            if (firstItem != null) {
                val moreCount = order.items.size - 1
                binding.tvFirstItemInfo.text = if (moreCount > 0) {
                    "${firstItem.productName} (+$moreCount barang lainnya)"
                } else {
                    firstItem.productName
                }
            } else {
                binding.tvFirstItemInfo.text = "Pesanan #${order.orderNumber}"
            }

            // Status Badge
            when (order.status) {
                "PAID" -> {
                    binding.tvOrderStatus.text = "Pembayaran Berhasil"
                    binding.tvOrderStatus.setTextColor(ContextCompat.getColor(context, R.color.success))
                    binding.tvOrderStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.success_container))
                }
                "FAILED" -> {
                    binding.tvOrderStatus.text = "Pembayaran Gagal"
                    binding.tvOrderStatus.setTextColor(ContextCompat.getColor(context, R.color.error))
                    binding.tvOrderStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.error_container))
                }
                else -> {
                    binding.tvOrderStatus.text = "Menunggu Pembayaran"
                    binding.tvOrderStatus.setTextColor(ContextCompat.getColor(context, R.color.warning))
                    binding.tvOrderStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.warning_container))
                }
            }

            binding.root.setOnClickListener { onItemClick(order) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderDto>() {
        override fun areItemsTheSame(oldItem: OrderDto, newItem: OrderDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderDto, newItem: OrderDto): Boolean {
            return oldItem == newItem
        }
    }
}
