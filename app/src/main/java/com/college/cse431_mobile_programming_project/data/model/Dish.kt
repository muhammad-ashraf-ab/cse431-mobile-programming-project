package com.college.cse431_mobile_programming_project.data.model

data class Dish(
    val id: Int,
    val name: String,
    val price: Float,
    val currency: String,
    val description: String,
    val rating: Float,
    val reviewers: Int,
    val img_path: String
)
