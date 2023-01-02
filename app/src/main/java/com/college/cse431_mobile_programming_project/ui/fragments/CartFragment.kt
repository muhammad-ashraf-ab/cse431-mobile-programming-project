package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.databases.CartDatabase
import com.college.cse431_mobile_programming_project.data.databases.DishesDatabase
import com.college.cse431_mobile_programming_project.data.model.DishCart
import com.college.cse431_mobile_programming_project.ui.adapter.CartRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentCartBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.ui.view_model.CartViewModel
import com.college.cse431_mobile_programming_project.ui.view_model.DishesViewModel
import com.college.cse431_mobile_programming_project.utils.OnQuantityChangeListener
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.CartViewModelFactory
import com.college.cse431_mobile_programming_project.utils.viewmodel_factory.DishesViewModelFactory

class CartFragment : Fragment() {

    private var cart = ArrayList<DishCart>()
    private lateinit var cartViewModel: CartViewModel
    private lateinit var dishesViewModel: DishesViewModel

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(this,
            CartViewModelFactory(
                CartDatabase.getDatabase(requireContext()).cartDao()
            )
        )[CartViewModel::class.java]

        dishesViewModel = ViewModelProvider(this,
            DishesViewModelFactory(
                DishesDatabase.getDatabase(requireContext()).dishDao(),
            )
        )[DishesViewModel::class.java]

        cartViewModel.getCartItems().observe(viewLifecycleOwner) { cartItems ->
            val dishIds = cartItems.map { it.dishId }
            dishesViewModel.getDishes(dishIds).observe(viewLifecycleOwner) { dishes ->
                cart = dishes.zip(cartItems).map { (dish, item) ->
                    DishCart(dish, item.amount)
                } as ArrayList<DishCart>

                binding.totalPrice.text = getCartTotalText()

                if (cart.isNotEmpty()) {
                    val cartRecyclerView = binding.cartRecyclerView
                    val cartRecyclerAdapter = CartRecyclerAdapter(cart, cartViewModel, object : OnQuantityChangeListener {
                        override fun onQuantityChange(position: Int, amount: Int) {
                            cart[position].amount = amount
                            binding.totalPrice.text = getCartTotalText()
                        }
                    })
                    cartRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    cartRecyclerView.adapter = cartRecyclerAdapter

                    if (binding.cartRecyclerView.id == binding.cartViewSwitcher.nextView.id) {
                        binding.cartViewSwitcher.showNext()
                    }
                }
                else if (binding.cartEmptyView.id == binding.cartViewSwitcher.nextView.id) {
                    binding.cartViewSwitcher.showNext()
                    binding.cartEmptyView.setOnClickListener {
                        it.findNavController().navigate(CartFragmentDirections.actionCartFragmentToMainFragment())
                    }
                }

            }
        }

        binding.proceedToCheckoutButton.setOnClickListener {
            it.findNavController().navigate(CartFragmentDirections.actionCartFragmentToPaymentFragment(cart.map { view -> view.dish.price!! * view.amount }.sum()))
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars("Cart", false, View.VISIBLE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCartTotalText(): String {
        return "Total: ${cart.map { it.dish.price!! * it.amount }.sum()}"
    }

}