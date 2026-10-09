package com.shopika.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shopika.app.data.model.ProductDto

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Long,
    val originalPrice: Long?,
    val discountPercent: Int,
    val category: String,
    val imageUrl: String,
    val rating: Double,
    val soldCount: Int,
    val stock: Int,
    val variantsCsv: String,
    val isPopular: Boolean,
    val isRecommended: Boolean
) {
    fun toDto(): ProductDto {
        return ProductDto(
            id = id,
            name = name,
            description = description,
            price = price,
            originalPrice = originalPrice,
            discountPercent = discountPercent,
            category = category,
            imageUrl = imageUrl,
            rating = rating,
            soldCount = soldCount,
            stock = stock,
            variants = if (variantsCsv.isBlank()) emptyList() else variantsCsv.split(",").map { it.trim() },
            isPopular = isPopular,
            isRecommended = isRecommended
        )
    }

    companion object {
        fun fromDto(dto: ProductDto): ProductEntity {
            return ProductEntity(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                price = dto.price,
                originalPrice = dto.originalPrice,
                discountPercent = dto.discountPercent,
                category = dto.category,
                imageUrl = dto.imageUrl,
                rating = dto.rating,
                soldCount = dto.soldCount,
                stock = dto.stock,
                variantsCsv = dto.variants.joinToString(","),
                isPopular = dto.isPopular,
                isRecommended = dto.isRecommended
            )
        }
    }
}
