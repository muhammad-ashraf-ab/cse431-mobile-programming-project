package com.college.cse431_mobile_programming_project.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.data.databases.UserDatabase
import com.college.cse431_mobile_programming_project.data.model.login.LoggedInUser
import com.college.cse431_mobile_programming_project.databinding.FragmentProfileBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.LoginViewModel
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.LoginViewModelFactory
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var user: LoggedInUser

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val programsArrayAdapter = ArrayAdapter(requireContext(), R.layout.profile_spinner_item, resources.getStringArray(R.array.programs))
        programsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.programSpinner.adapter = programsArrayAdapter

        val levelsArrayAdapter = ArrayAdapter(requireContext(), R.layout.profile_spinner_item, resources.getStringArray(R.array.levels))
        levelsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.levelSpinner.adapter = levelsArrayAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this,
            LoginViewModelFactory(UserDatabase.getDatabase(requireContext()).userDao())
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(viewLifecycleOwner) {
            user = it
            updateProfileUI()
        }

        binding.changeDisplayName.setOnClickListener {
            showDisplayNameEditText()
        }

        binding.confirmDisplayName.setOnClickListener {
            loginViewModel.updateDisplayName(binding.displayNameEdittext.text.toString())
            binding.displayName.text = if (user.displayName != "")
                user.displayName else user.email
            Toast.makeText(requireContext(), "It may require you to refresh page for changes to occur.", Toast.LENGTH_LONG).show()
            hideDisplayNameEditText()
        }

        binding.profilePicture.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loginResult.error?.let {
                    showErrorToast(it)
                }
            })

        binding.changeProgram.setOnClickListener {
            binding.programText.visibility = View.GONE
            binding.programSpinner.visibility = View.VISIBLE
        }

        binding.changeLevel.setOnClickListener {
            binding.levelText.visibility = View.GONE
            binding.levelSpinner.visibility = View.VISIBLE
        }

        binding.programSpinner.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                loginViewModel.setLoggedInUserProgram(binding.programSpinner.selectedItem.toString())
                binding.programText.text = getProgram()
                binding.programText.visibility = View.VISIBLE
                binding.programSpinner.visibility = View.GONE
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.programText.visibility = View.VISIBLE
                binding.programSpinner.visibility = View.GONE
            }
        }

        binding.levelSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                loginViewModel.setLoggedInUserLevel(binding.levelSpinner.selectedItem.toString())
                binding.levelText.text = getLevel()
                binding.levelText.visibility = View.VISIBLE
                binding.levelSpinner.visibility = View.GONE
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.levelText.visibility = View.VISIBLE
                binding.levelSpinner.visibility = View.GONE
            }
        }

        binding.logout.setOnClickListener {
            loginViewModel.logout()
            requireActivity().viewModelStore.clear()
            it.findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).configureBars("Profile", false, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(galleryIntent)
        } else {
            Toast.makeText(requireContext(), "Permission required to access gallery", Toast.LENGTH_SHORT).show()
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            updateProfilePicture(result.data!!.data!!)
        }
    }

    private fun showErrorToast(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_LONG).show()
    }

    private fun updateProfilePicture(imgUri: Uri) {
        loginViewModel.updateProfilePicture(imgUri)
        setProfilePicture(imgUri)
    }

    private fun setProfilePicture(imgUri: Uri) {
        try {
            Picasso.get().load(imgUri).into(binding.profilePicture)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "An unexpected error occurred while setting image", Toast.LENGTH_SHORT).show()
            binding.profilePicture.setImageResource(R.drawable.ic_baseline_person_32)
        }
    }

    private fun getProgram(): String {
        if (user.program != "") {
            return user.program
        }
        return "No program assigned."
    }

    private fun getLevel(): String {
        if (user.level != "") {
            return user.level
        }
        return "No level assigned."
    }

    private fun swapCardConstraints(edit: Boolean) {
        val cardParams = binding.programCardview.layoutParams as ConstraintLayout.LayoutParams
        cardParams.topToBottom = if (edit) binding.displayNameEdittext.id else binding.displayName.id
        binding.programCardview.requestLayout()
    }

    private fun showDisplayNameEditText() {
        binding.changeDisplayName.visibility = View.GONE
        binding.displayNameEdittext.visibility = View.VISIBLE
        swapCardConstraints(true)
        binding.confirmDisplayName.visibility = View.VISIBLE
        binding.displayName.visibility = View.GONE
        binding.displayNameEdittext.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.displayNameEdittext, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideDisplayNameEditText() {
        if (binding.displayNameEdittext.isFocused) {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
        binding.confirmDisplayName.visibility = View.GONE
        binding.displayName.visibility = View.VISIBLE
        swapCardConstraints(false)
        binding.changeDisplayName.visibility = View.VISIBLE
        binding.displayNameEdittext.visibility = View.GONE
    }

    private fun updateProfileUI() {
        val imgPath = user.profile_img_path
        if (!Uri.EMPTY.equals(imgPath) && imgPath.toString() != "") {
            Picasso.get().load(imgPath).into(binding.profilePicture)
        } else {
            binding.profilePicture.setImageResource(R.drawable.ic_baseline_person_32)
        }

        binding.displayName.text = if (user.displayName != "")
            user.displayName else user.email

        binding.displayNameEdittext.setText(binding.displayName.text)

        binding.programText.text = getProgram()
        binding.levelText.text = getLevel()

        binding.emailCardview.visibility = if (user.displayName != "") View.VISIBLE else View.GONE

        binding.email.text = user.email
    }

}