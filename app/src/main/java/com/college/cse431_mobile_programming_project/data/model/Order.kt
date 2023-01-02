package com.college.cse431_mobile_programming_project.data.model

data class Order(
    val orderId: Int = 0,
    val userId: String = "",
    val restaurantId: Int = 0,
    val status: String = "",
    val total: String = ""
)
