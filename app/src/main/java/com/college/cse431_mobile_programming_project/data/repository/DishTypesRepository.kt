package com.college.cse431_mobile_programming_project.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.college.cse431_mobile_programming_project.data.model.DishType
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DishTypesRepository {
    private val db = Firebase.database

    @Volatile private var INSTANCE: DishTypesRepository ?= null

    fun getInstance(): DishTypesRepository {
        return INSTANCE ?: synchronized(this){
            val instance = DishTypesRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadDishTypes(dishTypesList: MutableLiveData<List<DishType>>) {
        db.getReference("dish_types").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _dishTypesList : List<DishType> = snapshot.children.map {
                        it.getValue(DishType::class.java)!!
                    }
                    dishTypesList.postValue(_dishTypesList)
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}