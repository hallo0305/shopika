package com.shopika.app.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shopika.app.data.local.ShopikaDatabase
import com.shopika.app.data.remote.ApiClient
import com.shopika.app.data.repository.CartRepository
import com.shopika.app.data.repository.OrderRepository
import com.shopika.app.data.repository.ProductRepository
import com.shopika.app.data.repository.WishlistRepository

class ViewModelFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(productRepository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(cartRepository) as T
            }
            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> {
                CheckoutViewModel(orderRepository) as T
            }
            modelClass.isAssignableFrom(PaymentViewModel::class.java) -> {
                PaymentViewModel(orderRepository) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(orderRepository) as T
            }
            modelClass.isAssignableFrom(WishlistViewModel::class.java) -> {
                WishlistViewModel(wishlistRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                val db = ShopikaDatabase.getDatabase(context)
                val api = ApiClient.apiService
                val productRepo = ProductRepository(api, db.productDao())
                val cartRepo = CartRepository(api, db.cartDao())
                val orderRepo = OrderRepository(api, db.orderDao(), db.cartDao())
                val wishlistRepo = WishlistRepository(db.favoriteDao(), db.productDao())

                val newInstance = ViewModelFactory(productRepo, cartRepo, orderRepo, wishlistRepo)
                instance = newInstance
                newInstance
            }
        }
    }
}
