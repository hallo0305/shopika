package com.shopika.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.model.OrderDto

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val orderNumber: String,
    val itemsJson: String,
    val subtotal: Long,
    val shippingFee: Long,
    val totalAmount: Long,
    val status: String,
    val paymentMethod: String,
    val vaNumber: String,
    val shippingAddress: String,
    val recipientName: String,
    val recipientPhone: String,
    val createdAt: Long
) {
    fun toDto(): OrderDto {
        val type = object : TypeToken<List<CartItemDto>>() {}.type
        val items: List<CartItemDto> = try {
            Gson().fromJson(itemsJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
        return OrderDto(
            id = id,
            orderNumber = orderNumber,
            items = items,
            subtotal = subtotal,
            shippingFee = shippingFee,
            totalAmount = totalAmount,
            status = status,
            paymentMethod = paymentMethod,
            vaNumber = vaNumber,
            shippingAddress = shippingAddress,
            recipientName = recipientName,
            recipientPhone = recipientPhone,
            createdAt = createdAt
        )
    }

    companion object {
        fun fromDto(dto: OrderDto): OrderEntity {
            return OrderEntity(
                id = dto.id,
                orderNumber = dto.orderNumber,
                itemsJson = Gson().toJson(dto.items),
                subtotal = dto.subtotal,
                shippingFee = dto.shippingFee,
                totalAmount = dto.totalAmount,
                status = dto.status,
                paymentMethod = dto.paymentMethod,
                vaNumber = dto.vaNumber,
                shippingAddress = dto.shippingAddress,
                recipientName = dto.recipientName,
                recipientPhone = dto.recipientPhone,
                createdAt = dto.createdAt
            )
        }
    }
}
