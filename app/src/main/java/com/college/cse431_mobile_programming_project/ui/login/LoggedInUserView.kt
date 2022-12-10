package com.college.cse431_mobile_programming_project.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val profile_picture_url: String
    //... other data fields that may be accessible to the UI
)