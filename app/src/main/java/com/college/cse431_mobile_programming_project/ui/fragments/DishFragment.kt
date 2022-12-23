package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.databinding.FragmentDishBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel
import com.college.cse431_mobile_programming_project.utils.RestaurantsViewModelFactory
import com.squareup.picasso.Picasso

class DishFragment : Fragment() {

    private var dish: Dish = Dish()
    private lateinit var restaurantsViewModel: RestaurantsViewModel

    private var amount = 1

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private val args: DishFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishBinding.inflate(inflater, container, false)
        binding.description.movementMethod = ScrollingMovementMethod()

        restaurantsViewModel = ViewModelProvider(this,
            RestaurantsViewModelFactory(args.restaurantName, args.dishName))[RestaurantsViewModel::class.java]

        restaurantsViewModel.dish.observe(viewLifecycleOwner) {
            dish = it
            updateUI()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.decreaseAmountButton.setOnClickListener {
            amount = binding.amount.text.toString().toInt()
            if (amount > 1) {
                --amount
                val totalPrice = "${dish.currency} ${dish.price * amount}"
                binding.totalPrice.text = totalPrice
                binding.price.visibility = if (amount >= 2) View.VISIBLE else View.GONE
            }
            else {
                binding.price.visibility = View.GONE
            }
            binding.amount.text = amount.toString().padStart(2, '0')
        }

        binding.increaseAmountButton.setOnClickListener {
            amount = binding.amount.text.toString().toInt()
            if (amount < 99) {
                ++amount
                val totalPrice = "${dish.currency} ${dish.price * amount}"
                binding.totalPrice.text = totalPrice
                binding.price.visibility = View.VISIBLE
            }
            binding.amount.text = amount.toString().padStart(2, '0')
        }

        binding.addToCartButton.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars(args.dishName, true, View.GONE)
    }

    private fun updateUI() {
        val totalPrice = "${dish.currency} ${dish.price * amount}"
        val pricePerItem = "(${dish.price} per item)"
        binding.dishName.text = dish.name
        binding.totalPrice.text = totalPrice
        binding.price.text = pricePerItem
        if (amount <= 1)
            binding.price.visibility = View.GONE
        binding.description.text = dish.long_description
        Picasso.get().load(dish.img_path).into(binding.dishImage)
    }

}