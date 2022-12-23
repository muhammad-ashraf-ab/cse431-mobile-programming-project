package com.college.cse431_mobile_programming_project.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RestaurantsRepository {
    private val restaurantsRef = Firebase.database.getReference("restaurants")

    init {
        restaurantsRef.keepSynced(true)
    }

    @Volatile private var INSTANCE: RestaurantsRepository ?= null

    fun getInstance(): RestaurantsRepository {
        return INSTANCE ?: synchronized(this){
            val instance = RestaurantsRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadRestaurants(restaurantsList: MutableLiveData<List<Restaurant>>) {
        restaurantsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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
        restaurantsRef.orderByChild("name").equalTo(restaurantName).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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

    fun loadDish(dish: MutableLiveData<Dish>, restaurantName: String, dishName: String) {
        restaurantsRef.orderByChild("name").equalTo(restaurantName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val childKey : String = snapshot.children.first().key!!
                restaurantsRef.child("$childKey/dishes").orderByChild("name").equalTo(dishName).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("livedata", "restaurantslist ${dish.hasActiveObservers()}")
                        try {
                            val _dish : Dish = snapshot.children.map {
                                it.getValue(Dish::class.java)!!
                            }[0]
                            dish.value = _dish
                        } catch (e: Exception) {
                            Log.d("dberr", e.toString())
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}