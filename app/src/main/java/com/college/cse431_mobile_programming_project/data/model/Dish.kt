package com.college.cse431_mobile_programming_project.data.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "dishes")
//data class Dish(
//    @PrimaryKey
//    val id: Int? = 0,
//    val name: String? = "",
//    val price: Float? = 0.0f,
//    val currency: String? = "",
//    val short_description: String? = "",
//    val long_description: String? = "",
//    val img_path: String? = "",
//    val available: Boolean? = true
//)
data class Dish(
    val id: Int = 0,
    val name: String = "",
    val price: Float = 0.0f,
    val currency: String = "",
    val short_description: String = "",
    val long_description: String = "",
    val img_path: String = "",
    val available: Boolean = true
)
