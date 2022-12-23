package com.college.cse431_mobile_programming_project.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RestaurantsRepository {
    private val db = Firebase.database

    @Volatile private var INSTANCE: RestaurantsRepository ?= null

    fun getInstance(): RestaurantsRepository {
        return INSTANCE ?: synchronized(this){
            val instance = RestaurantsRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadRestaurants(restaurantsList: MutableLiveData<List<Restaurant>>) {
        db.getReference("restaurants").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("livedata", "restaurantslist ${restaurantsList.hasActiveObservers()}")
                try {
                    val _restaurantsList : List<Restaurant> = snapshot.children.map {
                        it.getValue(Restaurant::class.java)!!
                    }
                    restaurantsList.value = _restaurantsList
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun loadRestaurant(restaurant: MutableLiveData<Restaurant>, restaurantName: String) {
        db.getReference("restaurants").orderByChild("name").equalTo(restaurantName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("livedata", "restaurant ${restaurant.hasObservers()}")
                try {
                    val _restaurant : Restaurant = snapshot.children.map {
                        it.getValue(Restaurant::class.java)!!
                    }[0]
                    restaurant.value = _restaurant
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}