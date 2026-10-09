package com.shopika.app.ui.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopika.app.data.model.OrderDto
import com.shopika.app.data.model.PaymentResponse
import com.shopika.app.data.repository.OrderRepository
import com.shopika.app.util.Resource
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _order = MutableLiveData<Resource<OrderDto>>()
    val order: LiveData<Resource<OrderDto>> = _order

    private val _paymentResult = MutableLiveData<Resource<PaymentResponse>>()
    val paymentResult: LiveData<Resource<PaymentResponse>> = _paymentResult

    private val _formattedTimeRemaining = MutableLiveData("23:59:59")
    val formattedTimeRemaining: LiveData<String> = _formattedTimeRemaining

    private var countDownTimer: CountDownTimer? = null

    fun loadOrder(orderId: String) {
        viewModelScope.launch {
            _order.value = Resource.Loading
            val result = orderRepository.getOrderById(orderId)
            _order.value = result
        }
    }

    fun startCountdown(durationMillis: Long = 24 * 60 * 60 * 1000L) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (1000 * 60 * 60)
                val minutes = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60)
                val seconds = (millisUntilFinished % (1000 * 60)) / 1000
                _formattedTimeRemaining.value = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                _formattedTimeRemaining.value = "00:00:00"
            }
        }.start()
    }

    fun simulatePaymentSuccess(orderId: String) {
        viewModelScope.launch {
            _paymentResult.value = Resource.Loading
            val result = orderRepository.simulatePayment(orderId, "PAID")
            _paymentResult.value = result
        }
    }

    fun checkPaymentStatus(orderId: String) {
        viewModelScope.launch {
            _order.value = Resource.Loading
            val result = orderRepository.getOrderById(orderId)
            _order.value = result
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}
