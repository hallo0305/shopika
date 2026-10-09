package com.shopika.app.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopika.app.databinding.ActivityOrderHistoryBinding
import com.shopika.app.ui.adapter.OrderHistoryAdapter
import com.shopika.app.ui.viewmodel.OrderViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.util.Resource

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]

        setupRecyclerView()
        setupListeners()
        observeData()

        orderViewModel.loadOrders()
    }

    private fun setupRecyclerView() {
        orderHistoryAdapter = OrderHistoryAdapter { order ->
            val intent = Intent(this, OrderStatusActivity::class.java).apply {
                putExtra(OrderStatusActivity.EXTRA_ORDER_ID, order.id)
                putExtra(OrderStatusActivity.EXTRA_STATUS, order.status)
            }
            startActivity(intent)
        }

        binding.rvOrderHistory.apply {
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
            adapter = orderHistoryAdapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun observeData() {
        orderViewModel.orders.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.layoutEmptyOrders.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val orders = resource.data
                    if (orders.isEmpty()) {
                        binding.layoutEmptyOrders.visibility = View.VISIBLE
                        orderHistoryAdapter.submitList(emptyList())
                    } else {
                        binding.layoutEmptyOrders.visibility = View.GONE
                        orderHistoryAdapter.submitList(orders)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutEmptyOrders.visibility = View.VISIBLE
                    orderHistoryAdapter.submitList(emptyList())
                }
            }
        }
    }
}
