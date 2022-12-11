package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.databinding.FragmentDishBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.squareup.picasso.Picasso

class DishFragment : Fragment() {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private val args: DishFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dish = Dish(1,
            "Fool Sandwich",
            5.0f,
            "EGP",
            "A finely made fool sandwich made of the most exquisite of ingredients. The beans were grown in a farm with water taken directly from melting Antarctican glaciers. The bread was baked in the gates of hell. The tahini was created by crushing real human bones for the most exquisite of tastes. Truly a sandwich to fall in love with.",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7")

        var totalPrice = "${dish.currency} ${dish.price}"
        val pricePerItem = "(${dish.price} per item)"
        binding.dishName.text = dish.name
        binding.totalPrice.text = totalPrice
        binding.price.text = pricePerItem
        binding.price.visibility = View.INVISIBLE
        binding.description.text = dish.description
        Picasso.get().load(dish.img_path).into(binding.dishImage)

        binding.decreaseAmountButton.setOnClickListener {
            var amount = binding.amount.text.toString().toInt()
            if (amount > 1) {
                --amount
                totalPrice = "${dish.currency} ${dish.price * amount}"
                binding.totalPrice.text = totalPrice
                binding.price.visibility = if (amount >= 2) View.VISIBLE else View.INVISIBLE
            }
            else {
                binding.price.visibility = View.INVISIBLE
            }
            binding.amount.text = amount.toString().padStart(2, '0')
        }

        binding.increaseAmountButton.setOnClickListener {
            var amount = binding.amount.text.toString().toInt()
            if (amount < 99) {
                ++amount
                totalPrice = "${dish.currency} ${dish.price * amount}"
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

}