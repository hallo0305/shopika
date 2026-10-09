package com.shopika.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopika.app.data.model.CartItemDto
import com.shopika.app.data.model.CheckoutRequest
import com.shopika.app.data.model.OrderDto
import com.shopika.app.data.repository.OrderRepository
import com.shopika.app.util.Resource
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    // Alamat pengiriman Kota Cirebon sesuai spesifikasi UAS
    val recipientName = MutableLiveData("Raffi Nur Islami")
    val recipientPhone = MutableLiveData("+62 812-3456-7890")
    val shippingAddress = MutableLiveData("Jl. Pemuda No. 45, Sunyaragi, Kec. Kesambi, Kota Cirebon, Jawa Barat 45132")

    // Pilihan kurir: Reguler (Rp12.000) vs Express (Rp24.000)
    val selectedShippingMethod = MutableLiveData("Reguler (2-3 Hari)")
    val shippingFee = MutableLiveData(12000L)

    private val _checkoutResult = MutableLiveData<Resource<OrderDto>>()
    val checkoutResult: LiveData<Resource<OrderDto>> = _checkoutResult

    fun selectShippingMethod(isExpress: Boolean) {
        if (isExpress) {
            selectedShippingMethod.value = "Express (1 Hari)"
            shippingFee.value = 24000L
        } else {
            selectedShippingMethod.value = "Reguler (2-3 Hari)"
            shippingFee.value = 12000L
        }
    }

    fun calculateTotal(subtotal: Long): Long {
        return subtotal + (shippingFee.value ?: 12000L)
    }

    fun submitCheckout(items: List<CartItemDto>, notes: String?) {
        if (items.isEmpty()) {
            _checkoutResult.value = Resource.Error("Tidak ada produk yang dipilih")
            return
        }

        viewModelScope.launch {
            _checkoutResult.value = Resource.Loading
            val request = CheckoutRequest(
                items = items,
                shippingAddress = shippingAddress.value.orEmpty(),
                recipientName = recipientName.value.orEmpty(),
                recipientPhone = recipientPhone.value.orEmpty(),
                shippingMethod = selectedShippingMethod.value.orEmpty(),
                shippingFee = shippingFee.value ?: 12000L,
                paymentMethod = "Virtual Account BCA",
                notes = notes
            )
            val result = orderRepository.checkout(request)
            _checkoutResult.value = result
        }
    }
}
