package com.college.cse431_mobile_programming_project.data.databases

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.college.cse431_mobile_programming_project.data.model.Dish

//@Database(entities = [Dish::class], version = 1, exportSchema = false)
//abstract class DishesDatabase: RoomDatabase() {
abstract class DishesDatabase {
}
//    abstract fun dishDao(): DishesDao
//
//    companion object {
//        private var INSTANCE: DishesDatabase? = null
//        fun getDatabase(context: Context): DishesDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    DishesDatabase::class.java,
//                    "dishes"
//                ).fallbackToDestructiveMigration().build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}