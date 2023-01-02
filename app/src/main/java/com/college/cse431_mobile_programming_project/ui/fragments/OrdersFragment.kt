package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.databases.RestaurantDatabase
import com.college.cse431_mobile_programming_project.data.databases.UserDatabase
import com.college.cse431_mobile_programming_project.data.model.Order
import com.college.cse431_mobile_programming_project.data.model.OrderRecyclerItem
import com.college.cse431_mobile_programming_project.databinding.FragmentOrdersBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.adapter.OrdersAdapter
import com.college.cse431_mobile_programming_project.ui.view_model.LoginViewModel
import com.college.cse431_mobile_programming_project.ui.view_model.RestaurantsViewModel
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.LoginViewModelFactory
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.RestaurantsViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class OrdersFragment : Fragment() {

    private var orders: ArrayList<OrderRecyclerItem> = arrayListOf()
    private lateinit var restaurantsViewModel: RestaurantsViewModel
    private lateinit var loginViewModel: LoginViewModel

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        val ordersRecyclerView = binding.ordersRecyclerview
        val ordersRecyclerAdapter = OrdersAdapter(orders)
        ordersRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        ordersRecyclerView.adapter = ordersRecyclerAdapter

        restaurantsViewModel = ViewModelProvider(this,
            RestaurantsViewModelFactory(
                RestaurantDatabase.getDatabase(requireContext()).restaurantDao()
            )
        )[RestaurantsViewModel::class.java]

        loginViewModel = ViewModelProvider(this,
            LoginViewModelFactory(UserDatabase.getDatabase(requireContext()).userDao())
        )[LoginViewModel::class.java]

        Firebase.database.getReference("orders/${Firebase.auth.currentUser!!.uid}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    Log.d("bruher", snapshot.toString())
                    snapshot.children.map { dataSnapshot ->
                        val order = dataSnapshot.getValue(Order::class.java)!!
                        restaurantsViewModel.getAllRestaurants().observe(viewLifecycleOwner) { restaurants ->
                            val restaurant = restaurants.filter { restaurant ->
                                restaurant.id == order.restaurantId
                            }[0]
                            Log.d("bruher", restaurant.toString())
                            orders.add(OrderRecyclerItem(order.orderId, restaurant.name!!, restaurant.img_path!!, order.total, order.status))
                            ordersRecyclerAdapter.updateOrdersList(orders)

                            if (orders.isNotEmpty()) {
                                if (binding.ordersRecyclerview.id == binding.ordersViewSwitcher.nextView.id) {
                                    binding.ordersViewSwitcher.showNext()
                                }
                            }
                            else if (binding.noOrdersFoundText.id == binding.ordersViewSwitcher.nextView.id) {
                                binding.ordersViewSwitcher.showNext()
                                binding.noOrdersFoundText.setOnClickListener {
                                    it.findNavController().navigate(OrdersFragmentDirections.actionOrdersFragmentToCartFragment())
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("dberr", e.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("Order History", false, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}