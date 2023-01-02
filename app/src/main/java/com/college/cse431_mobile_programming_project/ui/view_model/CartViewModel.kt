package com.college.cse431_mobile_programming_project.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.databases.CartDao
import com.college.cse431_mobile_programming_project.data.model.CartItem
import com.college.cse431_mobile_programming_project.data.repository.CartRepository

class CartViewModel (
    cartDao: CartDao
) : ViewModel(){
    private val repository: CartRepository = CartRepository(cartDao).getInstance()

    fun addToCart(dish: CartItem) {
        repository.addToCart(dish)
    }

    fun getCartItems(): LiveData<List<CartItem>> {
        return repository.getCartItems()
    }

    fun removeFromCart(dishId: Int) {
        repository.removeFromCart(dishId)
    }

    fun clearCart() {
        repository.clearCart()
    }
}