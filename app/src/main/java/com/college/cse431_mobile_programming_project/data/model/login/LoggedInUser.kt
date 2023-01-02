package com.college.cse431_mobile_programming_project.data.model.login

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LoggedInUser(
    @PrimaryKey
    val user_id: String = "",
    val email: String = "",
    var displayName: String = "",
    var profile_img_path: Uri = Uri.EMPTY,
    var program: String = "",
    var level: String = ""
)