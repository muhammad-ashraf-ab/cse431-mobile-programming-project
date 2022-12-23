package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel
import com.college.cse431_mobile_programming_project.ui.adapter.RestaurantsRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentDishTypeBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity

class DishTypeFragment : Fragment() {

    private val restaurantsList = ArrayList<Restaurant>()
    private lateinit var restaurantsViewModel : RestaurantsViewModel

    private var _binding: FragmentDishTypeBinding? = null
    private val binding get() = _binding!!
    private val args: DishTypeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishTypeBinding.inflate(inflater, container, false)
        val view = binding.root

        val restaurantsRecyclerView = binding.restaurantsRecyclerview
        val restaurantsRecyclerAdapter = RestaurantsRecyclerAdapter(restaurantsList)
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        restaurantsRecyclerView.adapter = restaurantsRecyclerAdapter

        restaurantsViewModel = ViewModelProvider(this)[RestaurantsViewModel::class.java]

        restaurantsViewModel.restaurantsList.observe(viewLifecycleOwner) { restaurants ->
            val filteredRestaurants = ArrayList<Restaurant>()
            restaurants.forEach {
                if (args.tag in it.tags)
                    filteredRestaurants.add(it)
            }

            if (filteredRestaurants.isNotEmpty()) {
                restaurantsRecyclerAdapter.updateRestaurantsList(filteredRestaurants)

                if (binding.restaurantsRecyclerview.id == binding.restaurantsViewSwitcher.nextView.id) {
                    binding.restaurantsViewSwitcher.showNext()
                }
            } else if (binding.noRestaurantsFoundText.id == binding.restaurantsViewSwitcher.nextView.id) {
                binding.restaurantsViewSwitcher.showNext()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars(args.tag, true, View.GONE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}