package com.college.cse431_mobile_programming_project.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.repository.RestaurantsRepository

class RestaurantsViewModel : ViewModel() {
    private val repository : RestaurantsRepository = RestaurantsRepository().getInstance()
    private val _restaurantsList = MutableLiveData<List<Restaurant>>()
    val restaurantsList : LiveData<List<Restaurant>> = _restaurantsList

    init {
        repository.loadRestaurants(_restaurantsList)
    }
}