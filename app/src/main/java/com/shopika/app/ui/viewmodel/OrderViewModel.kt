package com.shopika.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopika.app.data.model.OrderDto
import com.shopika.app.data.repository.OrderRepository
import com.shopika.app.util.Resource
import kotlinx.coroutines.launch

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _orders = MutableLiveData<Resource<List<OrderDto>>>()
    val orders: LiveData<Resource<List<OrderDto>>> = _orders

    private val _orderDetail = MutableLiveData<Resource<OrderDto>>()
    val orderDetail: LiveData<Resource<OrderDto>> = _orderDetail

    fun loadOrders() {
        viewModelScope.launch {
            _orders.value = Resource.Loading
            val result = orderRepository.getOrders()
            _orders.value = result
        }
    }

    fun loadOrderDetail(orderId: String) {
        viewModelScope.launch {
            _orderDetail.value = Resource.Loading
            val result = orderRepository.getOrderById(orderId)
            _orderDetail.value = result
        }
    }
}
