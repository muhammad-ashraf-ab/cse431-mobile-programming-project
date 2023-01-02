package com.college.cse431_mobile_programming_project.data.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.college.cse431_mobile_programming_project.data.model.Restaurant

@Dao
interface RestaurantsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurants ORDER BY id ASC")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    fun getRestaurant(id: Int): LiveData<Restaurant>
}