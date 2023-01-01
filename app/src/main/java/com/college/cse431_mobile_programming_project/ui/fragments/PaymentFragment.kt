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
import java.util.Calendar

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

        binding.paymentMethodRadioGroup.setOnCheckedChangeListener { group, buttonId ->
            when (buttonId) {
                binding.paymentMethodCash.id -> {
                    binding.creditCardInfoLayout.visibility = View.GONE
                }
                binding.paymentMethodCreditCard.id -> {
                    binding.creditCardInfoLayout.visibility = View.VISIBLE
                }
            }
        }

        binding.pickupTime12pm.isEnabled = isTimeValid(10)
        binding.pickupTime12pm.alpha = if (binding.pickupTime12pm.isEnabled) 1f else 0.5f

        binding.pickupTime3pm.isEnabled = isTimeValid(13)
        binding.pickupTime3pm.alpha = if (binding.pickupTime3pm.isEnabled) 1f else 0.5f

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

        (activity as MainActivity).configureBars("Credit Card Payment", true, View.GONE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isTimeValid(hour: Int) : Boolean {
        val currentTime = Calendar.getInstance()
        val timeToMatch = Calendar.getInstance()

        timeToMatch[Calendar.HOUR_OF_DAY] = hour
        timeToMatch[Calendar.MINUTE] = 0

        return when {
            currentTime < timeToMatch -> true
            else -> false
        }
    }

}