package com.college.cse431_mobile_programming_project.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.databases.RestaurantsDao
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.repository.RestaurantsRepository

class RestaurantsViewModel(
    restaurantsDao: RestaurantsDao,
    restaurantId: Int = -1,
) : ViewModel() {
    private val repository : RestaurantsRepository = RestaurantsRepository(restaurantsDao).getInstance()

    fun getAllRestaurants(): LiveData<List<Restaurant>> {
        return repository.getAllRestaurants()
    }

    fun getRestaurant(id: Int): LiveData<Restaurant> {
        return repository.getRestaurant(id)
    }

    init {
        if (restaurantId == -1) {
            repository.loadRestaurants()
        }
    }
}