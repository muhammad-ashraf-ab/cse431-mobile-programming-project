package com.college.cse431_mobile_programming_project.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.college.cse431_mobile_programming_project.data.databases.UserDao
import com.college.cse431_mobile_programming_project.data.repository.LoginRepository
import com.college.cse431_mobile_programming_project.ui.view_model.LoginViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(userDao)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}