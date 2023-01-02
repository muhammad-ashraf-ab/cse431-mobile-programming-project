package com.college.cse431_mobile_programming_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey
    val id: Int? = 0,
    val name: String? = "",
    val rating: Float? = 0.0f,
    val reviewers: Int? = 0,
    val img_path: String? = "",
    val tags: ArrayList<String>? = arrayListOf(),
)
