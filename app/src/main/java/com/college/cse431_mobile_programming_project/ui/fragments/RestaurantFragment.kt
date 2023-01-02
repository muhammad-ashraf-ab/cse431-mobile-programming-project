package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel
import com.college.cse431_mobile_programming_project.ui.adapter.DishesRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentRestaurantBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.utils.RestaurantsViewModelFactory

class RestaurantFragment : Fragment() {

    private var dishesList = ArrayList<Dish>()
    private lateinit var restaurantsViewModel: RestaurantsViewModel

    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private val args: RestaurantFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        val view = binding.root

        val dishesRecyclerView = binding.dishesRecyclerview
        val dishesRecyclerAdapter = DishesRecyclerAdapter(dishesList, args.restaurantId)
        dishesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dishesRecyclerView.adapter = dishesRecyclerAdapter

        restaurantsViewModel = ViewModelProvider(this, RestaurantsViewModelFactory(args.restaurantId))[RestaurantsViewModel::class.java]

        restaurantsViewModel.restaurant.observe(viewLifecycleOwner) {
            (activity as MainActivity).configureBars(restaurantsViewModel.restaurant.value?.name!!, true, View.GONE)
            if (it.dishes.isNotEmpty()) {
                dishesRecyclerAdapter.updateDishesList(it.dishes)

                if (binding.dishesRecyclerview.id == binding.dishesViewSwitcher.nextView.id) {
                    binding.dishesViewSwitcher.showNext()
                }
            } else if (binding.noDishesFoundText.id == binding.dishesViewSwitcher.nextView.id) {
                binding.dishesViewSwitcher.showNext()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val restaurantName = restaurantsViewModel.restaurant.value?.name ?: ""
        (activity as MainActivity).configureBars(restaurantName, true, View.GONE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}