package com.college.cse431_mobile_programming_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey
    val dishId: Int,
    var amount: Int
)
