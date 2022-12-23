package com.college.cse431_mobile_programming_project.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.repository.RestaurantsRepository

class RestaurantsViewModel(restaurantName: String = "Burger Restaurant") : ViewModel() {
    private val repository : RestaurantsRepository = RestaurantsRepository().getInstance()

    private val _restaurantsList = MutableLiveData<List<Restaurant>>()
    val restaurantsList : LiveData<List<Restaurant>> = _restaurantsList

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant : LiveData<Restaurant> = _restaurant

    init {
        TODO("Uncomment after fixing arguments.")
//        if (restaurantName == "") {
            repository.loadRestaurants(_restaurantsList)
//        }
//        else {
            repository.loadRestaurant(_restaurant, restaurantName)
//        }
    }
}