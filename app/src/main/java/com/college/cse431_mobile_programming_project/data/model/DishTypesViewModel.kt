package com.college.cse431_mobile_programming_project.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.college.cse431_mobile_programming_project.data.repository.DishTypesRepository

class DishTypesViewModel : ViewModel() {
    private val repository = DishTypesRepository().getInstance()
    private val _dishTypesList = MutableLiveData<List<DishType>>()
    val dishTypesList : LiveData<List<DishType>> = _dishTypesList

    init {
        repository.loadDishTypes(_dishTypesList)
    }
}