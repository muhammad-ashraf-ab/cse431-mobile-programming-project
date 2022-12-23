package com.college.cse431_mobile_programming_project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel

class RestaurantsViewModelFactory(restaurantName: String = "", dishName : String = "") : ViewModelProvider.Factory {
    private val _restaurantName = restaurantName
    private val _dishName = dishName
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantsViewModel(_restaurantName, _dishName) as T
    }
}