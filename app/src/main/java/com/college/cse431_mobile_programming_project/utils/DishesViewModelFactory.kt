package com.college.cse431_mobile_programming_project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.data.databases.DishesDao
import com.college.cse431_mobile_programming_project.ui.view_model.DishesViewModel

class DishesViewModelFactory(
    private val dishesDao: DishesDao,
    private val restaurantId: Int = -1,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DishesViewModel(dishesDao, restaurantId) as T
    }
}