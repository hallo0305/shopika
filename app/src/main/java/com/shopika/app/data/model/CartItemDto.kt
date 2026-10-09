package com.shopika.app.data.model

import com.google.gson.annotations.SerializedName

data class CartItemDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("product_price")
    val productPrice: Long,
    @SerializedName("product_image")
    val productImage: String,
    @SerializedName("variant")
    val variant: String,
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("stock")
    val stock: Int = 50,
    @SerializedName("is_selected")
    var isSelected: Boolean = true
)
