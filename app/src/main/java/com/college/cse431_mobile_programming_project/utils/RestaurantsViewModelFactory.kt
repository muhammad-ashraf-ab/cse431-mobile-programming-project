package com.college.cse431_mobile_programming_project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel

class RestaurantsViewModelFactory(param: String) : ViewModelProvider.Factory {
    private val _param = param
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantsViewModel(_param) as T
    }
}