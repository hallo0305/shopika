package com.shopika.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shopika.app.data.model.ProductDto
import com.shopika.app.data.repository.WishlistRepository
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    val favoriteIds: LiveData<List<String>> = wishlistRepository.getFavoriteIdsFlow().asLiveData()

    private val _favoriteProducts = MutableLiveData<List<ProductDto>>()
    val favoriteProducts: LiveData<List<ProductDto>> = _favoriteProducts

    fun loadFavorites() {
        viewModelScope.launch {
            _favoriteProducts.value = wishlistRepository.getFavoriteProducts()
        }
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            wishlistRepository.toggleFavorite(productId)
            loadFavorites()
        }
    }
}
