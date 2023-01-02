package com.college.cse431_mobile_programming_project.utils

import androidx.room.TypeConverter
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class RestaurantConverters {

    @TypeConverter
    fun fromArrayListToJson(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToArrayList(value: String): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromDishesListToJson(list: ArrayList<Dish>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToDishes(value: String): ArrayList<Dish> {
        val listType: Type = object : TypeToken<ArrayList<Dish>>() {}.type
        return Gson().fromJson(value, listType)
    }
}