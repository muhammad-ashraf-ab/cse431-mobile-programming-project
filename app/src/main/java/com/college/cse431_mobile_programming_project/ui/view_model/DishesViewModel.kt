package com.college.cse431_mobile_programming_project.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.databases.DishesDao
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.repository.DishesRepository

class DishesViewModel (
    dishesDao: DishesDao,
    restaurantId: Int = -1,
) : ViewModel() {
    private val repository : DishesRepository = DishesRepository(dishesDao).getInstance()

    init {
        if (restaurantId != -1) {
            repository.loadDishes(restaurantId)
        }
    }

    fun getRestaurantDishes(restaurantId: Int): LiveData<List<Dish>> {
        return repository.getRestaurantDishes(restaurantId)
    }

    fun getDish(dishId: Int): LiveData<Dish> {
        return repository.getDish(dishId)
    }
}