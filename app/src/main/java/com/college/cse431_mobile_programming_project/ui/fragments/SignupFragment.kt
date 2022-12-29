package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

class SignupFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

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

        val emailEditText = binding.emailEditText
        val passwordEditText = binding.passwordEdit
        val confirmPasswordEditText = binding.confirmPasswordEdit
        val signupButton = binding.signupButton
        val loadingProgressBar = binding.loading

        loginViewModel.signupFormState.observe(viewLifecycleOwner,
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

        binding.signupButton.setOnClickListener {
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