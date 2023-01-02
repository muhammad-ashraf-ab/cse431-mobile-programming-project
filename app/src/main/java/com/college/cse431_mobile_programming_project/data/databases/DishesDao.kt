package com.college.cse431_mobile_programming_project.data.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.college.cse431_mobile_programming_project.data.model.Dish

@Dao
interface DishesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDish(dish: Dish)

    @Query("SELECT * FROM dishes WHERE restaurant_id = :restaurantId")
    fun getRestaurantDishes(restaurantId: Int): LiveData<List<Dish>>

    @Query("SELECT * FROM dishes WHERE id = :dishId")
    fun getDish(dishId: Int): LiveData<Dish>

    @Query("SELECT * FROM dishes WHERE id in (:dishIds)")
    fun getDishes(dishIds: List<Int>): LiveData<List<Dish>>
}