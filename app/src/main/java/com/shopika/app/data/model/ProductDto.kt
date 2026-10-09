package com.shopika.app.data.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Long,
    @SerializedName("original_price")
    val originalPrice: Long? = null,
    @SerializedName("discount_percent")
    val discountPercent: Int = 0,
    @SerializedName("category")
    val category: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("rating")
    val rating: Double = 4.8,
    @SerializedName("sold_count")
    val soldCount: Int = 120,
    @SerializedName("stock")
    val stock: Int = 50,
    @SerializedName("variants")
    val variants: List<String> = emptyList(),
    @SerializedName("is_popular")
    val isPopular: Boolean = false,
    @SerializedName("is_recommended")
    val isRecommended: Boolean = true
)
