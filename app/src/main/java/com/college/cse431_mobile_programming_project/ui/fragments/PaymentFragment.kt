package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.college.cse431_mobile_programming_project.databinding.FragmentPaymentBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalPrice = "EGP ${args.totalPrice}"
        binding.totalPrice.text = totalPrice

        binding.payNowButton.setOnClickListener {
            it.findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToOrderCompleteFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("Credit Card Payment", true, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}