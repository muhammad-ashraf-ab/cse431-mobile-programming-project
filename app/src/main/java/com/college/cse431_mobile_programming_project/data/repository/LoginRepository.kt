package com.college.cse431_mobile_programming_project.data.repository

import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser
import com.college.cse431_mobile_programming_project.utils.Result
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        // TODO: revoke authentication
    }

    fun emailPasswordLogin(email: String = "John Doe", password: String, resultCallback: (Result<LoggedInUser>) -> Unit) {
        var result: Result<LoggedInUser>
        val firebaseAuth = Firebase.auth
        var loggedInUser: LoggedInUser
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    loggedInUser = LoggedInUser(
                        firebaseAuth.currentUser!!.uid,
                        firebaseAuth.currentUser!!.email!!,
                        ""
                    )
                    result = Result.Success(loggedInUser)
                    setLoggedInUser(loggedInUser)
                    resultCallback(result as Result.Success<LoggedInUser>)
                } else {
                    result = Result.Error(it.exception!!)
                    resultCallback(result as Result.Error)
                }
            }
        } catch (e: Exception) {
            result = Result.Error(e)
            resultCallback(result as Result.Error)
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}