package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.databases.DishesDatabase
import com.college.cse431_mobile_programming_project.data.databases.RestaurantDatabase
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel
import com.college.cse431_mobile_programming_project.ui.adapter.DishesRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentRestaurantBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.DishesViewModel
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.DishesViewModelFactory
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.RestaurantsViewModelFactory

class RestaurantFragment : Fragment() {

    private var dishesList = ArrayList<Dish>()
    private lateinit var restaurantsViewModel: RestaurantsViewModel
    private lateinit var dishesViewModel: DishesViewModel

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

        restaurantsViewModel = ViewModelProvider(this,
            RestaurantsViewModelFactory(
                RestaurantDatabase.getDatabase(requireContext()).restaurantDao(),
                args.restaurantId
            )
        )[RestaurantsViewModel::class.java]

        dishesViewModel = ViewModelProvider(this,
            DishesViewModelFactory(
                DishesDatabase.getDatabase(requireContext()).dishDao(),
                args.restaurantId
            )
        )[DishesViewModel::class.java]

        restaurantsViewModel.getRestaurant(args.restaurantId).observe(viewLifecycleOwner) { restaurant ->
            (activity as MainActivity).configureBars(restaurant.name!!, true, View.GONE)
            dishesViewModel.getRestaurantDishes(restaurant.id!!).observe(viewLifecycleOwner) { dishes ->
                if (dishes.isNotEmpty()) {
                    dishesRecyclerAdapter.updateDishesList(dishes)

                    if (binding.dishesRecyclerview.id == binding.dishesViewSwitcher.nextView.id) {
                        binding.dishesViewSwitcher.showNext()
                    }
                } else if (binding.noDishesFoundText.id == binding.dishesViewSwitcher.nextView.id) {
                    binding.dishesViewSwitcher.showNext()
                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val restaurantName = restaurantsViewModel.getRestaurant(args.restaurantId).value?.name ?: ""
        (activity as MainActivity).configureBars(restaurantName, true, View.GONE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}