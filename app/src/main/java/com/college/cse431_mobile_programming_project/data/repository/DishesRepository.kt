package com.college.cse431_mobile_programming_project.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.college.cse431_mobile_programming_project.data.databases.DishesDao
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DishesRepository(private val dishesDao: DishesDao) {
    private val dishesRef = Firebase.database.getReference("dishes")

    private var dishes: ArrayList<Dish> = arrayListOf()

    init {
        dishesRef.keepSynced(true)
    }

    @Volatile private var INSTANCE: DishesRepository ?= null

    fun getInstance() : DishesRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = DishesRepository(dishesDao)
            INSTANCE = instance
            instance
        }
    }

    fun loadDishes(restaurantId: Int) {
        dishesRef.orderByChild("restaurant_id").equalTo(restaurantId.toDouble()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    snapshot.children.map {
                        val dish = it.getValue(Dish::class.java)!!
                        dishes.add(dish)
                        CoroutineScope(Dispatchers.IO).launch {
                            dishesDao.addDish(dish)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun getRestaurantDishes(restaurantId: Int): LiveData<List<Dish>> {
        return dishesDao.getRestaurantDishes(restaurantId)
    }

    fun getDish(dishId: Int): LiveData<Dish> {
        return dishesDao.getDish(dishId)
    }

    fun getDishes(dishIds: List<Int>): LiveData<List<Dish>> {
        return dishesDao.getDishes(dishIds)
    }
}