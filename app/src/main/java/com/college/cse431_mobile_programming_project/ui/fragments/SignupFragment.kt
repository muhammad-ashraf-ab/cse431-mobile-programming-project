package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser
import com.college.cse431_mobile_programming_project.databinding.FragmentSignupBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.LoginViewModel
import com.college.cse431_mobile_programming_project.utils.LoginViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.signupButton.isEnabled = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity(),
            LoginViewModelFactory()
        )[LoginViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val googleSignInRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val data = it.data
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                loginViewModel.firebaseAuthWithGoogleAccount(account)
            }
            catch (e: Exception) {
                Log.d("signIn", e.toString())
                Toast.makeText(context?.applicationContext, "Google Login Failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.googleSignInButton.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            googleSignInRequest.launch(intent)
        }

        val emailEditText = binding.emailEditText
        val displayNameEditText = binding.displayNameEdit
        val passwordEditText = binding.passwordEdit
        val confirmPasswordEditText = binding.confirmPasswordEdit
        val signupButton = binding.signupButton
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { signupFormState ->
                signupFormState ?: return@Observer
                signupButton.isEnabled = signupFormState.isDataValid
                signupFormState.emailError?.let {
                    emailEditText.error = getString(it)
                }
                signupFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
                signupFormState.confirmPasswordError?.let {
                    confirmPasswordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showSignupFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToMainFragment())
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                loginViewModel.signupDataChanged(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString()
                )
            }
        }
        emailEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener)

        signupButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.signup(
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                displayNameEditText.text.toString()
            )
        }

        binding.login.setOnClickListener {
            it.findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
        }
    }

    private fun updateUiWithUser(model: LoggedInUser) {
        val name = if (model.displayName != "") model.displayName else model.email
        val welcome = getString(R.string.welcome) + name
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showSignupFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).configureBars("", false, View.GONE, false)
    }
}