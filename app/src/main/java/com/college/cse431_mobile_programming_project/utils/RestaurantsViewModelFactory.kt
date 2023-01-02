package com.college.cse431_mobile_programming_project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel

class RestaurantsViewModelFactory(restaurantId: Int = -1, dishId : Int = -1) : ViewModelProvider.Factory {
    private val _restaurantName = restaurantId
    private val _dishName = dishId
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantsViewModel(_restaurantName, _dishName) as T
    }
}