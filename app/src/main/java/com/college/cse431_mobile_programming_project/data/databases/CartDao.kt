package com.college.cse431_mobile_programming_project.data.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.college.cse431_mobile_programming_project.data.model.CartItem

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToCart(dish: CartItem)

    @Query("SELECT * FROM cart")
    fun getCartItems(): LiveData<List<CartItem>>

    @Query("DELETE FROM cart WHERE dishId = :dishId")
    fun removeFromCart(dishId: Int)

    @Query("DELETE FROM cart")
    fun clearCart()
}