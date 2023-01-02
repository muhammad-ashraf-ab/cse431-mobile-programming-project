package com.college.cse431_mobile_programming_project.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.utils.RestaurantConverters

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
@TypeConverters(RestaurantConverters::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantsDao

    companion object {
        private var INSTANCE: RestaurantDatabase? = null
        fun getDatabase(context: Context): RestaurantDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "restaurants"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}