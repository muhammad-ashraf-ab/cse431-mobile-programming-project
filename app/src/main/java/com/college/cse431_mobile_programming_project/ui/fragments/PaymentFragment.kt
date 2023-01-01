package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.college.cse431_mobile_programming_project.R
import com.college.cse431_mobile_programming_project.databinding.FragmentPaymentBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import java.util.Calendar

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val args: PaymentFragmentArgs by navArgs()

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
            if (isFormValid()) {
                findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToOrderCompleteFragment())
            }
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

    private fun isTimeValid(hour: Int): Boolean {
        val currentTime = Calendar.getInstance()
        val timeToMatch = Calendar.getInstance()

        timeToMatch[Calendar.HOUR_OF_DAY] = hour
        timeToMatch[Calendar.MINUTE] = 0

        return when {
            currentTime < timeToMatch -> true
            else -> false
        }
    }

    private fun isFormValid(): Boolean {
        return isPaymentSelected() && isGateSelected() && isTimeSelected() && isSelectedTimeValid()
    }

    private fun isPaymentSelected(): Boolean {
        val isPaymentSelected = binding.paymentMethodRadioGroup.checkedRadioButtonId >= 0
        if (!isPaymentSelected)
            giveGroupError(binding.paymentMethodRadioGroup)
        return isPaymentSelected
    }

    private fun isGateSelected(): Boolean {
        val isGateSelected = binding.pickupLocationRadioGroup.checkedRadioButtonId >= 0
        if (!isGateSelected)
            giveGroupError(binding.pickupLocationRadioGroup)
        return isGateSelected
    }

    private fun isTimeSelected(): Boolean {
        return if (isSelectedTimeValid()) {
            val isTimeSelected = binding.pickupTimeRadioGroup.checkedRadioButtonId >= 0
            if (!isTimeSelected)
                giveGroupError(binding.pickupTimeRadioGroup)
            isTimeSelected
        } else {
            false
        }
    }

    private fun isSelectedTimeValid(): Boolean {
        val timeGroup = binding.pickupTimeRadioGroup
        return when {
            !(isTimeValid(10) || isTimeValid(13)) -> {
                Toast.makeText(
                    requireContext(),
                    "Sorry, no more orders can be placed today.",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
            !isTimeValid(10) && (timeGroup.checkedRadioButtonId == binding.pickupTime12pm.id) -> {
                Toast.makeText(
                    requireContext(),
                    "Sorry, you cannot place an order for 12PM anymore. Consider placing it for 3PM instead.",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
            !isTimeValid(13) && (timeGroup.checkedRadioButtonId == binding.pickupTime3pm.id) -> {
                Toast.makeText(
                    requireContext(),
                    "Sorry, no more orders can be placed today.",
                    Toast.LENGTH_LONG
                ).show()
                false
            }
            else -> true
        }
    }

    private fun giveGroupError(group: RadioGroup) {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.error_shake)
        group.startAnimation(animation)
    }

}