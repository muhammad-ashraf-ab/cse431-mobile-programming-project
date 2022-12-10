package com.college.cse431_mobile_programming_project.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val user_id: String,
    val display_name: String,
    val profile_img_path: String
)