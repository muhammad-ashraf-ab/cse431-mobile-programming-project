package com.college.cse431_mobile_programming_project.data.databases

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: LoggedInUser)

    @Query("SELECT * FROM users WHERE user_id = :uid")
    fun getUser(uid: String): LiveData<LoggedInUser>

    @Query("UPDATE users SET profile_img_path = :imgUri WHERE user_id = :uid")
    fun updateProfilePicture(uid: String, imgUri: Uri)

    @Query("UPDATE users SET program = :program WHERE user_id = :uid")
    fun updateProgram(uid: String, program: String)

    @Query("UPDATE users SET level = :level WHERE user_id = :uid")
    fun updateLevel(uid: String, level: String)

    @Query("UPDATE users SET displayName = :displayName WHERE user_id = :uid")
    fun updateDisplayName(uid: String, displayName: String)
}