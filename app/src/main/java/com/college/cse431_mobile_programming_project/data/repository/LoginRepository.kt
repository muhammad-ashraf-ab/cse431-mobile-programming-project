package com.college.cse431_mobile_programming_project.data.repository

import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser
import com.college.cse431_mobile_programming_project.utils.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
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
        Firebase.auth.signOut()
    }

    fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?, resultCallback: (Result<LoggedInUser>) -> Unit) {
        val firebaseAuth = Firebase.auth
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val firebaseUser = firebaseAuth.currentUser

                val uid = firebaseUser!!.uid
                val email = firebaseUser.email
                val displayName = firebaseUser.displayName
                val imgUrl = firebaseUser.photoUrl

                val loggedInUser = LoggedInUser(uid, email!!, displayName!!, imgUrl.toString())
                setLoggedInUser(loggedInUser)
                resultCallback(Result.Success(loggedInUser))
            } else {
                resultCallback(Result.Error(it.exception!!))
            }
        }
    }

    fun emailPasswordLogin(email: String, password: String, resultCallback: (Result<LoggedInUser>) -> Unit) {
        var result: Result<LoggedInUser>
        val firebaseAuth = Firebase.auth
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val loggedInUser = LoggedInUser(
                        firebaseAuth.currentUser!!.uid,
                        firebaseAuth.currentUser!!.email!!,
                        firebaseAuth.currentUser!!.displayName.let { firebaseAuth.currentUser!!.displayName } ?: "",
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

    fun signup(email: String, password: String, displayName: String, resultCallback: (Result<LoggedInUser>) -> Unit) {
        var result: Result<LoggedInUser>
        val firebaseAuth = Firebase.auth
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    if (displayName != "") {
                        val profileUpdates = userProfileChangeRequest {
                            this.displayName = displayName
                        }
                        Firebase.auth.currentUser!!.updateProfile(profileUpdates)
                    }
                    val loggedInUser = LoggedInUser(
                        firebaseAuth.currentUser!!.uid,
                        firebaseAuth.currentUser!!.email!!,
                        firebaseAuth.currentUser!!.displayName.let { firebaseAuth.currentUser!!.displayName } ?: "",
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