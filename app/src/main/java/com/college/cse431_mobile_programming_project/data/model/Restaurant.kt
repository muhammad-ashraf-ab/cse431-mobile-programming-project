package com.college.cse431_mobile_programming_project.data.model

data class Restaurant(
    val id: Int,
    val name: String,
    val rating: Float,
    val reviewers: Int,
    val img_path: String,
    val tags: ArrayList<String>,
    val dishes: ArrayList<Dish>
)
