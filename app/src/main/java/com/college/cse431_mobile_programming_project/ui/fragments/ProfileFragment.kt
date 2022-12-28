package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.college.cse431_mobile_programming_project.databinding.FragmentProfileBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlin.random.Random

class ProfileFragment : Fragment() {

    private lateinit var imgPath : String
    private val names = arrayOf("John Doe", "John Smith", "David Rodriguez", "Maria Rodriguez", "Maria Martinez", "Mary Garcia")
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var randomNumberGenerator: Random

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        randomNumberGenerator = Random(System.currentTimeMillis())
        imgPath = "https://randomuser.me/api/portraits/${arrayOf("men", "women")[randomNumberGenerator.nextInt(2)]}/${randomNumberGenerator.nextInt(1, 50)}.jpg"
        Log.d("imgPath", imgPath)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(imgPath).into(binding.profilePicture)
        binding.emailField.text = names[randomNumberGenerator.nextInt(names.size)]

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
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