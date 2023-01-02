package com.college.cse431_mobile_programming_project.utils.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.data.databases.CartDao
import com.college.cse431_mobile_programming_project.ui.view_model.CartViewModel

class CartViewModelFactory(
    private val cartDao: CartDao
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(cartDao) as T
    }
}