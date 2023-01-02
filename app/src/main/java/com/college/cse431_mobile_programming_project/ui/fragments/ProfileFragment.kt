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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.databinding.FragmentProfileBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.LoginViewModel
import com.college.cse431_mobile_programming_project.utils.LoginViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

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

        loginViewModel = ViewModelProvider(requireActivity(),
            LoginViewModelFactory()
        )[LoginViewModel::class.java]

        // TODO: save user locally to use here
//        val imgPath = loginViewModel.loginResult.value!!.success!!.profile_img_path
        val imgPath = Firebase.auth.currentUser!!.photoUrl.toString()
        if (imgPath != "" && imgPath != "null") {
            Picasso.get().load(imgPath).into(binding.profilePicture)
        } else {
            binding.profilePicture.setImageResource(R.drawable.ic_baseline_person_32)
        }
//        binding.displayName.text = loginViewModel.loginResult.value!!.success!!.displayName
        binding.displayName.text = if (Firebase.auth.currentUser!!.displayName != "" && Firebase.auth.currentUser!!.displayName != null)
            Firebase.auth.currentUser!!.displayName else Firebase.auth.currentUser!!.email

        binding.changeDisplayName.setOnClickListener {
            showDisplayNameEditText()
        }

        binding.confirmDisplayName.setOnClickListener {
            hideDisplayNameEditText()
        }

        binding.programText.text = getProgram()
        binding.levelText.text = getLevel()

        binding.emailCardview.visibility = if (Firebase.auth.currentUser!!.displayName != "" && Firebase.auth.currentUser!!.displayName != null)
            View.VISIBLE else View.GONE

        binding.email.text = Firebase.auth.currentUser!!.email

        binding.profilePicture.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

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

    private fun updateProfilePicture(imgUri: Uri) {
        val storageRef = Firebase.storage.reference
        val imgRef = storageRef.child("images/profile_pictures/${Firebase.auth.currentUser!!.uid}}")
        val uploadTask = imgRef.putFile(imgUri)

        try {
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imgRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setProfilePicture(task.result)
                }

            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "An unexpected error occurred while uploading image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setProfilePicture(imgUri: Uri?) {
        // TODO: set imgUri locally

        val profileUpdate = userProfileChangeRequest {
            photoUri = imgUri
        }
        Firebase.auth.currentUser!!.updateProfile(profileUpdate)

        try {
            Picasso.get().load(imgUri).into(binding.profilePicture)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "An unexpected error occurred while setting image", Toast.LENGTH_SHORT).show()
            binding.profilePicture.setImageResource(R.drawable.ic_baseline_person_32)
        }
    }

    private fun getProgram(): String {
        loginViewModel.loginResult.value?.let { loginResult ->
            loginResult.success?.let { loggedInUser ->
                if (loggedInUser.program != "") {
                    return loggedInUser.program
                }
            }
        }
        return "No program assigned."
    }

    private fun getLevel(): String {
        loginViewModel.loginResult.value?.let { loginResult ->
            loginResult.success?.let { loggedInUser ->
                if (loggedInUser.level != "") {
                    return loggedInUser.level
                }
            }
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
        binding.displayNameEdittext.text.clear()
        binding.displayNameEdittext.visibility = View.GONE
    }

}