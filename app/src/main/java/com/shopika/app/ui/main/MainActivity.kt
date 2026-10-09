package com.shopika.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shopika.app.R
import com.shopika.app.databinding.ActivityMainBinding
import com.shopika.app.ui.cart.CartFragment
import com.shopika.app.ui.category.CategoryFragment
import com.shopika.app.ui.home.HomeFragment
import com.shopika.app.ui.profile.ProfileFragment
import com.shopika.app.ui.viewmodel.CartViewModel
import com.shopika.app.ui.viewmodel.ViewModelFactory
import com.shopika.app.ui.wishlist.WishlistFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        setupBottomNavigation()
        observeCartBadge()

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.menu_category -> {
                    loadFragment(CategoryFragment())
                    true
                }
                R.id.menu_cart -> {
                    loadFragment(CartFragment())
                    true
                }
                R.id.menu_wishlist -> {
                    loadFragment(WishlistFragment())
                    true
                }
                R.id.menu_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    fun navigateToTab(menuId: Int) {
        binding.bottomNavigation.selectedItemId = menuId
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun observeCartBadge() {
        cartViewModel.cartItems.observe(this) { items ->
            val count = items.sumOf { it.quantity }
            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.menu_cart)
            if (count > 0) {
                badge.isVisible = true
                badge.number = count
                badge.backgroundColor = getColor(R.color.primary)
            } else {
                badge.isVisible = false
            }
        }
        cartViewModel.fetchCart()
    }
}
