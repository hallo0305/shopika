package com.shopika.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.repository.CartRepository
import com.shopika.app.util.Resource
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems: LiveData<List<CartItemDto>> = cartRepository.getLocalCartFlow().asLiveData()

    private val _cartOperationStatus = MutableLiveData<Resource<String>>()
    val cartOperationStatus: LiveData<Resource<String>> = _cartOperationStatus

    fun fetchCart() {
        viewModelScope.launch {
            cartRepository.getCart()
        }
    }

    fun addToCart(productId: String, variant: String, quantity: Int) {
        viewModelScope.launch {
            _cartOperationStatus.value = Resource.Loading
            val result = cartRepository.addToCart(productId, variant, quantity)
            when (result) {
                is Resource.Success -> _cartOperationStatus.value = Resource.Success("Berhasil ditambahkan ke keranjang")
                is Resource.Error -> _cartOperationStatus.value = Resource.Error(result.message)
                is Resource.Loading -> {}
            }
        }
    }

    fun updateQuantity(id: String, quantity: Int) {
        if (quantity < 1) return
        viewModelScope.launch {
            cartRepository.updateQuantity(id, quantity)
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            cartRepository.deleteItem(id)
        }
    }

    fun toggleSelection(id: String, isSelected: Boolean) {
        viewModelScope.launch {
            cartRepository.updateSelection(id, isSelected)
        }
    }

    fun toggleSelectAll(isSelected: Boolean) {
        viewModelScope.launch {
            cartRepository.updateAllSelection(isSelected)
        }
    }

    fun calculateSubtotal(items: List<CartItemDto>): Long {
        return items.filter { it.isSelected }.sumOf { it.productPrice * it.quantity }
    }

    fun getSelectedCount(items: List<CartItemDto>): Int {
        return items.filter { it.isSelected }.sumOf { it.quantity }
    }
}
