package com.college.cse431_mobile_programming_project.data.model.login

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val user_id: String,
    val email: String,
    val displayName: String,
    val profile_img_path: String,
    var program: String = "",
    var level: String = ""
)