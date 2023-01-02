package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.college.cse431_mobile_programming_project.databinding.FragmentOrderCompleteBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity

class OrderCompleteFragment : Fragment() {

    private var _binding: FragmentOrderCompleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.traceOrderButton.setOnClickListener {
            it.findNavController().navigate(OrderCompleteFragmentDirections.actionOrderCompleteFragmentToOrdersFragment())
        }

        binding.goBackHomeButton.setOnClickListener {
            it.findNavController().navigate(OrderCompleteFragmentDirections.actionOrderCompleteFragmentToMainFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("", true, View.INVISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}