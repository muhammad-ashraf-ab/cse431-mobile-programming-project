package com.college.cse431_mobile_programming_project.data.model

data class Dish(
    val id: Int,
    val name: String,
    val price: Float,
    val currency: String,
    val short_description: String,
    val long_description: String,
    val img_path: String,
    val available: Boolean = true
)
