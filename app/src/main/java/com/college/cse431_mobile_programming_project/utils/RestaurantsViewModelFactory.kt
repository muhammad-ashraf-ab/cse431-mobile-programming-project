package com.college.cse431_mobile_programming_project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.data.databases.RestaurantsDao
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel

class RestaurantsViewModelFactory(
    private val restaurantsDao: RestaurantsDao,
    private val restaurantId: Int = -1
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantsViewModel(restaurantsDao, restaurantId) as T
    }
}