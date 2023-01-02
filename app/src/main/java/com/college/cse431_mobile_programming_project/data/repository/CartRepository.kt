package com.college.cse431_mobile_programming_project.data.repository

import androidx.lifecycle.LiveData
import com.college.cse431_mobile_programming_project.data.databases.CartDao
import com.college.cse431_mobile_programming_project.data.model.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartRepository(private val cartDao: CartDao) {

    @Volatile private var INSTANCE: CartRepository ?= null

    fun getInstance() : CartRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = CartRepository(cartDao)
            INSTANCE = instance
            instance
        }
    }

    fun addToCart(dish: CartItem) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.addToCart(dish)
        }
    }

    fun getCartItems(): LiveData<List<CartItem>> {
        return cartDao.getCartItems()
    }

    fun removeFromCart(dishId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.removeFromCart(dishId)
        }
    }

    fun clearCart() {
        CoroutineScope(Dispatchers.IO).launch {
            cartDao.clearCart()
        }
    }
}