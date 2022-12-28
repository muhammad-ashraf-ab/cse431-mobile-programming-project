package com.college.cse431_mobile_programming_project.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.college.cse431_mobile_programming_project.data.repository.LoginRepository
import com.college.cse431_mobile_programming_project.utils.Result

import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUserView
import com.college.cse431_mobile_programming_project.data.model.login.LoginFormState
import com.college.cse431_mobile_programming_project.data.model.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun emailPasswordLogin(email: String, password: String) {
        loginRepository.emailPasswordLogin(email, password) { result ->
            if (result is Result.Success) {
            _loginResult.postValue(
                LoginResult(success = LoggedInUserView(email = result.data.email))
            )
            } else {
                val errMessage = when (result.getException()) {
                    "com.google.firebase.auth.FirebaseAuthInvalidUserException" -> R.string.authentication_failed
                    "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException" -> R.string.authentication_failed
                    "com.google.firebase.FirebaseNetworkException" -> R.string.network_error
                    else -> R.string.login_failed
                }

                _loginResult.postValue(LoginResult(error = errMessage))
            }
        }
    }

    fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount) {
        loginRepository.firebaseAuthWithGoogleAccount(account) { result ->
            if (result is Result.Success) {
                _loginResult.postValue(
                    LoginResult(success = LoggedInUserView(email = result.data.email))
                )
            } else {
                _loginResult.postValue(LoginResult(error = R.string.login_failed))
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isEmailValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches() and email.endsWith("@eng.asu.edu.eg")
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}