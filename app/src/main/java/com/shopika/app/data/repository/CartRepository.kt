package com.shopika.app.data.repository

import com.shopika.app.data.local.dao.CartDao
import com.shopika.app.data.local.entity.CartEntity
import com.shopika.app.data.model.AddToCartRequest
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.model.UpdateCartQuantityRequest
import com.shopika.app.data.remote.ApiService
import com.shopika.app.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CartRepository(
    private val apiService: ApiService,
    private val cartDao: CartDao
) {
    fun getLocalCartFlow(): Flow<List<CartItemDto>> {
        return cartDao.getAllFlow().map { list -> list.map { it.toDto() } }
    }

    suspend fun getCart(): Resource<List<CartItemDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCart()
                if (response.isSuccessful && response.body() != null) {
                    val remoteItems = response.body()!!
                    // Sinkronkan ke Room
                    cartDao.clearAll()
                    remoteItems.forEach { cartDao.insert(CartEntity.fromDto(it)) }
                    Resource.Success(remoteItems)
                } else {
                    val local = cartDao.getAll().map { it.toDto() }
                    Resource.Success(local)
                }
            } catch (e: Exception) {
                val local = cartDao.getAll().map { it.toDto() }
                Resource.Success(local)
            }
        }
    }

    suspend fun addToCart(productId: String, variant: String, quantity: Int): Resource<CartItemDto> {
        return withContext(Dispatchers.IO) {
            try {
                val request = AddToCartRequest(productId, variant, quantity)
                val response = apiService.addToCart(request)
                if (response.isSuccessful && response.body() != null) {
                    val item = response.body()!!
                    cartDao.insert(CartEntity.fromDto(item))
                    Resource.Success(item)
                } else {
                    Resource.Error("Gagal menambahkan ke keranjang: ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error("Koneksi gagal: ${e.localizedMessage ?: "Terjadi kesalahan"}", e)
            }
        }
    }

    suspend fun updateQuantity(id: String, quantity: Int): Resource<CartItemDto> {
        return withContext(Dispatchers.IO) {
            try {
                val request = UpdateCartQuantityRequest(quantity)
                val response = apiService.updateCartQuantity(id, request)
                if (response.isSuccessful && response.body() != null) {
                    val updated = response.body()!!
                    cartDao.updateQuantity(id, updated.quantity)
                    Resource.Success(updated)
                } else {
                    Resource.Error("Gagal memperbarui jumlah barang: ${response.message()}")
                }
            } catch (e: Exception) {
                // Update local fallback
                cartDao.updateQuantity(id, quantity)
                val local = cartDao.getById(id)?.toDto()
                if (local != null) {
                    Resource.Success(local)
                } else {
                    Resource.Error("Gagal memperbarui jumlah: ${e.localizedMessage}", e)
                }
            }
        }
    }

    suspend fun deleteItem(id: String): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.deleteCartItem(id)
                if (response.isSuccessful) {
                    cartDao.deleteById(id)
                    Resource.Success(true)
                } else {
                    Resource.Error("Gagal menghapus barang: ${response.message()}")
                }
            } catch (e: Exception) {
                // Hapus lokal
                cartDao.deleteById(id)
                Resource.Success(true)
            }
        }
    }

    suspend fun updateSelection(id: String, isSelected: Boolean) {
        withContext(Dispatchers.IO) {
            cartDao.updateSelection(id, isSelected)
        }
    }

    suspend fun updateAllSelection(isSelected: Boolean) {
        withContext(Dispatchers.IO) {
            cartDao.updateAllSelection(isSelected)
        }
    }
}
