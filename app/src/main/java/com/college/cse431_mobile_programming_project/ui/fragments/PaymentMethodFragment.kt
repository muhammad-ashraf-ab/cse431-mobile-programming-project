package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.college.cse431_mobile_programming_project.databinding.FragmentPaymentMethodBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity

class PaymentMethodFragment : Fragment() {

    private var _binding: FragmentPaymentMethodBinding? = null
    private val binding get() = _binding!!
    private val args: PaymentMethodFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.creditCardPaymentButton.setOnClickListener {
            //TODO: Fix bug
            it.findNavController().navigate(PaymentMethodFragmentDirections.actionPaymentMethodFragmentToPaymentFragment(args.totalPrice))
        }

        binding.cashPaymentButton.setOnClickListener {
            //TODO: Fix bug
            it.findNavController().navigate(PaymentMethodFragmentDirections.actionPaymentMethodFragmentToOrderCompleteFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("Payment Method", true, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}