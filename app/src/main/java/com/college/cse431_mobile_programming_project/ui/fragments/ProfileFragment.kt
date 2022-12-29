package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.databinding.FragmentProfileBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.LoginViewModel
import com.college.cse431_mobile_programming_project.utils.LoginViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
        if (imgPath != "") {
            Picasso.get().load(imgPath).into(binding.profilePicture)
        } else {
            binding.profilePicture.setImageResource(R.drawable.ic_baseline_person_32)
        }
//        binding.displayName.text = loginViewModel.loginResult.value!!.success!!.displayName
        binding.displayName.text = Firebase.auth.currentUser!!.displayName

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
}