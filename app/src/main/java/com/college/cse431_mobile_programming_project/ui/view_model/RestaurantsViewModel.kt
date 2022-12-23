package com.college.cse431_mobile_programming_project.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.repository.RestaurantsRepository

class RestaurantsViewModel(restaurantName: String = "", dishName : String = "") : ViewModel() {
    private val repository : RestaurantsRepository = RestaurantsRepository().getInstance()

    private val _restaurantsList = MutableLiveData<List<Restaurant>>()
    val restaurantsList : LiveData<List<Restaurant>> = _restaurantsList

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant : LiveData<Restaurant> = _restaurant

    private val _dish = MutableLiveData<Dish>()
    val dish : LiveData<Dish> = _dish

    init {
        if (restaurantName == "") {
            repository.loadRestaurants(_restaurantsList)
        }
        else if (dishName == ""){
            repository.loadRestaurant(_restaurant, restaurantName)
        }
        else {
            repository.loadDish(_dish, restaurantName, dishName)
        }
    }
}