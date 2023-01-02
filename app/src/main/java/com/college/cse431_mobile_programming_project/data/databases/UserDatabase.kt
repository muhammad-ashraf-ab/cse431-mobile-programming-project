package com.college.cse431_mobile_programming_project.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser
import com.college.cse431_mobile_programming_project.utils.UserConverters

@Database(entities = [LoggedInUser::class], version = 1, exportSchema = false)
@TypeConverters(UserConverters::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "users"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}