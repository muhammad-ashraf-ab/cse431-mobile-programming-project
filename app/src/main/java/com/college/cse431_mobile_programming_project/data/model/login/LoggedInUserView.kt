package com.college.cse431_mobile_programming_project.data.model.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
//    val uid: String,
    val email: String,
    val displayName: String,
    val profile_img_path: String
    //... other data fields that may be accessible to the UI
)