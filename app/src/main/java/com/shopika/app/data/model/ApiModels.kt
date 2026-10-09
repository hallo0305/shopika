package com.shopika.app.data.model

import com.google.gson.annotations.SerializedName

data class AddToCartRequest(
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("variant")
    val variant: String,
    @SerializedName("quantity")
    val quantity: Int
)

data class UpdateCartQuantityRequest(
    @SerializedName("quantity")
    val quantity: Int
)

data class CheckoutRequest(
    @SerializedName("items")
    val items: List<CartItemDto>,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("recipient_name")
    val recipientName: String,
    @SerializedName("recipient_phone")
    val recipientPhone: String,
    @SerializedName("shipping_method")
    val shippingMethod: String,
    @SerializedName("shipping_fee")
    val shippingFee: Long,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("notes")
    val notes: String? = null
)

data class OrderDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("order_number")
    val orderNumber: String,
    @SerializedName("items")
    val items: List<CartItemDto>,
    @SerializedName("subtotal")
    val subtotal: Long,
    @SerializedName("shipping_fee")
    val shippingFee: Long,
    @SerializedName("total_amount")
    val totalAmount: Long,
    @SerializedName("status")
    val status: String, // PENDING, PAID, FAILED
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("va_number")
    val vaNumber: String,
    @SerializedName("shipping_address")
    val shippingAddress: String,
    @SerializedName("recipient_name")
    val recipientName: String,
    @SerializedName("recipient_phone")
    val recipientPhone: String,
    @SerializedName("created_at")
    val createdAt: Long = System.currentTimeMillis()
)

data class PaymentResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
)

data class DeleteResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
