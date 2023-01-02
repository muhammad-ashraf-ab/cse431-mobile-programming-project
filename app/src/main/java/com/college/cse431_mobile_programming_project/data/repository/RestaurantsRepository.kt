package com.college.cse431_mobile_programming_project.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.college.cse431_mobile_programming_project.data.databases.RestaurantsDao
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantsRepository(private val restaurantsDao: RestaurantsDao) {
    private val restaurantsRef = Firebase.database.getReference("restaurants")

    private var restaurants: ArrayList<Restaurant> = arrayListOf()

    init {
        restaurantsRef.keepSynced(true)
    }

    @Volatile private var INSTANCE: RestaurantsRepository ?= null

    fun getInstance(): RestaurantsRepository {
        return INSTANCE ?: synchronized(this){
            val instance = RestaurantsRepository(restaurantsDao)
            INSTANCE = instance
            instance
        }
    }

    fun loadRestaurants() {
        restaurantsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    snapshot.children.map {
                        val restaurant = it.getValue(Restaurant::class.java)!!
                        restaurants.add(restaurant)
                        CoroutineScope(Dispatchers.IO).launch {
                            restaurantsDao.addRestaurant(restaurant)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun loadRestaurant(restaurant: MutableLiveData<Restaurant>, restaurantId: Int) {
        restaurantsRef.child("$restaurantId").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    restaurant.value = snapshot.getValue(Restaurant::class.java)!!
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun loadDish(dish: MutableLiveData<Dish>, restaurantId: Int, dishId: Int) {
        restaurantsRef.child("$restaurantId/dishes/$dishId").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    dish.value = snapshot.getValue(Dish::class.java)
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getAllRestaurants(): LiveData<List<Restaurant>> {
        return restaurantsDao.getAllRestaurants()
    }
}