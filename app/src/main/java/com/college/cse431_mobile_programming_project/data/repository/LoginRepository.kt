package com.college.cse431_mobile_programming_project.data.repository

import android.net.Uri
import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser
import com.college.cse431_mobile_programming_project.utils.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

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
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = firebaseAuth.currentUser

                val uid = firebaseUser!!.uid
                val email = firebaseUser.email
                val displayName = firebaseUser.displayName
                val imgUrl = firebaseUser.photoUrl

                val loggedInUser = LoggedInUser(uid, email!!, displayName!!, imgUrl ?: Uri.EMPTY)
                setLoggedInUser(loggedInUser)
                resultCallback(Result.Success(loggedInUser))
            } else {
                resultCallback(Result.Error(task.exception!!))
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
                        Uri.EMPTY
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
                        Uri.EMPTY
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

    fun updateProfilePicture(imgUri: Uri, resultCallback: (Result<LoggedInUser>) -> Unit) {
        var result: Result<LoggedInUser>
        val storageRef = Firebase.storage.reference
        val imgRef = storageRef.child("images/profile_pictures/${Firebase.auth.currentUser!!.uid}")
        val uploadTask = imgRef.putFile(imgUri)

        try {
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                imgRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setProfilePicture(task.result)
                } else {
                    result = Result.Error(task.exception!!)
                    resultCallback(result as Result.Error)
                }
            }
        } catch (e: Exception) {
            result = Result.Error(e)
            resultCallback(result as Result.Error)
        }
    }

    private fun setProfilePicture(imgUri: Uri) {
        // TODO: set imgUri locally

        val profileUpdate = userProfileChangeRequest {
            photoUri = imgUri
        }
        Firebase.auth.currentUser!!.updateProfile(profileUpdate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                this.user?.let {
                    it.profile_img_path = imgUri
                }
            }
        }

    }

    fun setLoggedInUserProgram(program: String) {
        this.user?.let {
            it.program = program
        }
        // TODO: Do it with Firebase and ROOM database
    }

    fun setLoggedInUserLevel(level: String) {
        this.user?.let {
            it.level = level
        }
        // TODO: Do it with Firebase and ROOM database
    }

    fun updateDisplayName(displayName: String) {
        val profileUpdates = userProfileChangeRequest {
            this.displayName = displayName
        }
        Firebase.auth.currentUser!!.updateProfile(profileUpdates)
        this.user?.let {
            it.displayName = displayName
        }
    }
}