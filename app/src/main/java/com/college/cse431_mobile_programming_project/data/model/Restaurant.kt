package com.college.cse431_mobile_programming_project.data.model

data class Restaurant(
    val id: Int = 0,
    val name: String = "",
    val rating: Float = 0.0f,
    val reviewers: Int = 0,
    val img_path: String = "",
    val tags: ArrayList<String> = arrayListOf(),
    val dishes: ArrayList<Dish> = arrayListOf()
)
