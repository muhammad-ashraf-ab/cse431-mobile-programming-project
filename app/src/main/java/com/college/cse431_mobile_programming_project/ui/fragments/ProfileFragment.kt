package com.college.cse431_mobile_programming_project.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginViewModel = ViewModelProvider(requireActivity(),
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

        binding.profilePicture.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
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

}