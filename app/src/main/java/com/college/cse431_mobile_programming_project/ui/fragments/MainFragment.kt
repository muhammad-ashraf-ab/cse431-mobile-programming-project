package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.DishType
import com.college.cse431_mobile_programming_project.ui.view_model.DishTypesViewModel
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel
import com.college.cse431_mobile_programming_project.ui.adapter.DishTypesRecyclerAdapter
import com.college.cse431_mobile_programming_project.ui.adapter.RestaurantsRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentMainBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private val restaurantsList = ArrayList<Restaurant>()
    private val dishTypesList = ArrayList<DishType>()
    private lateinit var restaurantsViewModel : RestaurantsViewModel
    private lateinit var dishTypesViewModel: DishTypesViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = Firebase.auth.currentUser
        if (user == null) {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        val dishesRecyclerView = binding.dishTypesRecyclerview
        val dishTypesRecyclerAdapter = DishTypesRecyclerAdapter(dishTypesList)
        dishesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        dishesRecyclerView.adapter = dishTypesRecyclerAdapter
        dishesRecyclerView.adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        dishTypesViewModel = ViewModelProvider(this)[DishTypesViewModel::class.java]

        dishTypesViewModel.dishTypesList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                dishTypesRecyclerAdapter.updateDishTypesList(it)

                if (binding.dishTypesRecyclerview.id == binding.dishesViewSwitcher.nextView.id) {
                    binding.dishesViewSwitcher.showNext()
                }
            }
            else if (binding.noDishesFoundText.id == binding.dishesViewSwitcher.nextView.id) {
                binding.dishesViewSwitcher.showNext()
            }
        }

        val restaurantsRecyclerView = binding.restaurantsRecyclerview
        val restaurantsRecyclerAdapter = RestaurantsRecyclerAdapter(restaurantsList)
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        restaurantsRecyclerView.adapter = restaurantsRecyclerAdapter
        restaurantsRecyclerView.adapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        restaurantsViewModel = ViewModelProvider(this)[RestaurantsViewModel::class.java]

        restaurantsViewModel.restaurantsList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                restaurantsRecyclerAdapter.updateRestaurantsList(it)
                if (binding.restaurantsRecyclerview.id == binding.restaurantsViewSwitcher.nextView.id) {
                    binding.restaurantsViewSwitcher.showNext()
                }
            }
            else if (binding.noRestaurantsFoundText.id == binding.restaurantsViewSwitcher.nextView.id) {
                binding.restaurantsViewSwitcher.showNext()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("Home", false, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}