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
                try {
                    val _restaurantsList : List<Restaurant> = snapshot.children.map {
                        it.getValue(Restaurant::class.java)!!
                    }
                    restaurantsList.postValue(_restaurantsList)
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}