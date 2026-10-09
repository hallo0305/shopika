package com.shopika.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopika.app.data.model.ProductDto
import com.shopika.app.data.repository.ProductRepository
import com.shopika.app.util.Resource
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<ProductDto>>>()
    val products: LiveData<Resource<List<ProductDto>>> = _products

    private val _popularProducts = MutableLiveData<List<ProductDto>>()
    val popularProducts: LiveData<List<ProductDto>> = _popularProducts

    private val _recommendedProducts = MutableLiveData<List<ProductDto>>()
    val recommendedProducts: LiveData<List<ProductDto>> = _recommendedProducts

    private val _productDetail = MutableLiveData<Resource<ProductDto>>()
    val productDetail: LiveData<Resource<ProductDto>> = _productDetail

    private val _searchResults = MutableLiveData<Resource<List<ProductDto>>>()
    val searchResults: LiveData<Resource<List<ProductDto>>> = _searchResults

    fun loadProducts(category: String? = null) {
        viewModelScope.launch {
            _products.value = Resource.Loading
            val result = productRepository.getProducts(category = category)
            _products.value = result

            if (result is Resource.Success) {
                _popularProducts.value = result.data.filter { it.isPopular }
                _recommendedProducts.value = result.data.filter { it.isRecommended }
            }
        }
    }

    fun loadProductDetail(id: String) {
        viewModelScope.launch {
            _productDetail.value = Resource.Loading
            val result = productRepository.getProductById(id)
            _productDetail.value = result
        }
    }

    fun searchProducts(query: String, category: String? = null) {
        viewModelScope.launch {
            _searchResults.value = Resource.Loading
            val result = productRepository.getProducts(search = query, category = category)
            _searchResults.value = result
        }
    }
}
