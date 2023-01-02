package com.college.cse431_mobile_programming_project.data.model.login

import android.net.Uri

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val user_id: String,
    val email: String,
    val displayName: String,
    var profile_img_path: Uri,
    var program: String = "",
    var level: String = ""
)