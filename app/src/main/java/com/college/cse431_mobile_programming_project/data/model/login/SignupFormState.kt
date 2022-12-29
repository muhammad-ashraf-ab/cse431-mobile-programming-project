package com.college.cse431_mobile_programming_project.data.model.login

data class SignupFormState(
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val confirmPasswordError: Int? = null,
    val isDataValid: Boolean = false
)
