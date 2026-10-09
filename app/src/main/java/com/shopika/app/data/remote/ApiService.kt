package com.shopika.app.data.remote

import com.shopika.app.data.model.AddToCartRequest
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.model.CheckoutRequest
import com.shopika.app.data.model.DeleteResponse
import com.shopika.app.data.model.OrderDto
import com.shopika.app.data.model.PaymentResponse
import com.shopika.app.data.model.ProductDto
import com.shopika.app.data.model.UpdateCartQuantityRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/products")
    suspend fun getProducts(
        @Query("search") search: String? = null,
        @Query("category") category: String? = null
    ): Response<List<ProductDto>>

    @GET("api/products/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): Response<ProductDto>

    @GET("api/cart")
    suspend fun getCart(): Response<List<CartItemDto>>

    @POST("api/cart")
    suspend fun addToCart(
        @Body request: AddToCartRequest
    ): Response<CartItemDto>

    @PUT("api/cart/{id}")
    suspend fun updateCartQuantity(
        @Path("id") id: String,
        @Body request: UpdateCartQuantityRequest
    ): Response<CartItemDto>

    @DELETE("api/cart/{id}")
    suspend fun deleteCartItem(
        @Path("id") id: String
    ): Response<DeleteResponse>

    @POST("api/checkout")
    suspend fun checkout(
        @Body request: CheckoutRequest
    ): Response<OrderDto>

    @POST("api/payment/simulate")
    suspend fun simulatePayment(
        @Query("order_id") orderId: String,
        @Query("status") status: String
    ): Response<PaymentResponse>

    @GET("api/orders")
    suspend fun getOrders(): Response<List<OrderDto>>

    @GET("api/orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: String
    ): Response<OrderDto>
}
