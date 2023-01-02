package com.college.cse431_mobile_programming_project.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.databases.RestaurantsDao
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.repository.RestaurantsRepository

class RestaurantsViewModel(restaurantsDao: RestaurantsDao, restaurantId: Int = -1, dishId : Int = -1) : ViewModel() {
    private val repository : RestaurantsRepository = RestaurantsRepository(restaurantsDao).getInstance()

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant : LiveData<Restaurant> = _restaurant

    private val _dish = MutableLiveData<Dish>()
    val dish : LiveData<Dish> = _dish

    fun getAllRestaurants(): LiveData<List<Restaurant>> {
        return repository.getAllRestaurants()
    }

    init {
        if (restaurantId == -1) {
            repository.loadRestaurants()
        }
        else if (dishId == -1){
            repository.loadRestaurant(_restaurant, restaurantId)
        }
        else {
            repository.loadDish(_dish, restaurantId, dishId)
        }
    }
}