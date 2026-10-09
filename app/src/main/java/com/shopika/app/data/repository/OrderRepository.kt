package com.shopika.app.data.repository

import com.shopika.app.data.local.dao.CartDao
import com.shopika.app.data.local.dao.OrderDao
import com.shopika.app.data.local.entity.OrderEntity
import com.shopika.app.data.model.CheckoutRequest
import com.shopika.app.data.model.OrderDto
import com.shopika.app.data.model.PaymentResponse
import com.shopika.app.data.remote.ApiService
import com.shopika.app.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepository(
    private val apiService: ApiService,
    private val orderDao: OrderDao,
    private val cartDao: CartDao
) {
    suspend fun checkout(request: CheckoutRequest): Resource<OrderDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.checkout(request)
                if (response.isSuccessful && response.body() != null) {
                    val order = response.body()!!
                    orderDao.insert(OrderEntity.fromDto(order))
                    // Bersihkan item dari cart lokal
                    cartDao.deleteSelected()
                    Resource.Success(order)
                } else {
                    Resource.Error("Gagal memproses pesanan: ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error("Gagal menghubungi server checkout: ${e.localizedMessage}", e)
            }
        }
    }

    suspend fun simulatePayment(orderId: String, status: String = "PAID"): Resource<PaymentResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.simulatePayment(orderId, status)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    orderDao.updateStatus(orderId, status)
                    Resource.Success(result)
                } else {
                    Resource.Error("Simulasi pembayaran gagal: ${response.message()}")
                }
            } catch (e: Exception) {
                orderDao.updateStatus(orderId, status)
                Resource.Success(
                    PaymentResponse(
                        success = true,
                        orderId = orderId,
                        status = status,
                        message = "Pembayaran berhasil disimulasikan (mode offline)"
                    )
                )
            }
        }
    }

    suspend fun getOrders(): Resource<List<OrderDto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getOrders()
                if (response.isSuccessful && response.body() != null) {
                    val list = response.body()!!
                    list.forEach { orderDao.insert(OrderEntity.fromDto(it)) }
                    Resource.Success(list)
                } else {
                    val local = orderDao.getAll().map { it.toDto() }
                    Resource.Success(local)
                }
            } catch (e: Exception) {
                val local = orderDao.getAll().map { it.toDto() }
                Resource.Success(local)
            }
        }
    }

    suspend fun getOrderById(orderId: String): Resource<OrderDto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getOrderById(orderId)
                if (response.isSuccessful && response.body() != null) {
                    val order = response.body()!!
                    orderDao.insert(OrderEntity.fromDto(order))
                    Resource.Success(order)
                } else {
                    val local = orderDao.getById(orderId) ?: orderDao.getByOrderNumber(orderId)
                    if (local != null) {
                        Resource.Success(local.toDto())
                    } else {
                        Resource.Error("Pesanan tidak ditemukan")
                    }
                }
            } catch (e: Exception) {
                val local = orderDao.getById(orderId) ?: orderDao.getByOrderNumber(orderId)
                if (local != null) {
                    Resource.Success(local.toDto())
                } else {
                    Resource.Error("Gagal memuat pesanan: ${e.localizedMessage}", e)
                }
            }
        }
    }
}
