package com.shopika.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shopika.app.data.model.CartItemDto

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey
    val id: String,
    val productId: String,
    val productName: String,
    val productPrice: Long,
    val productImage: String,
    val variant: String,
    val quantity: Int,
    val stock: Int,
    val isSelected: Boolean
) {
    fun toDto(): CartItemDto {
        return CartItemDto(
            id = id,
            productId = productId,
            productName = productName,
            productPrice = productPrice,
            productImage = productImage,
            variant = variant,
            quantity = quantity,
            stock = stock,
            isSelected = isSelected
        )
    }

    companion object {
        fun fromDto(dto: CartItemDto): CartEntity {
            return CartEntity(
                id = dto.id,
                productId = dto.productId,
                productName = dto.productName,
                productPrice = dto.productPrice,
                productImage = dto.productImage,
                variant = dto.variant,
                quantity = dto.quantity,
                stock = dto.stock,
                isSelected = dto.isSelected
            )
        }
    }
}
