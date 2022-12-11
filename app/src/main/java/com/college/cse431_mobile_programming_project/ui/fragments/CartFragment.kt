package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.DishesCart
import com.college.cse431_mobile_programming_project.data.recycler_data.CartRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentCartBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import kotlin.random.Random

class CartFragment : Fragment() {

    private var cart = ArrayList<DishesCart>()
    private lateinit var randomNumberGenerator: Random
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        randomNumberGenerator = Random(System.currentTimeMillis())

        cart.add(DishesCart(Dish(1,
                "Fool Sandwich",
                5.0f,
                "EGP",
                "Fool sandwich.",
                "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7"
        ), randomNumberGenerator.nextInt(1,5)))

        cart.add(DishesCart(Dish(2,
            "Falafel Sandwich",
            6.0f,
            "EGP",
            "Falafel sandwich.",
            "https://pbs.twimg.com/media/ErbexFhXUAAwJLS.jpg"
        ), randomNumberGenerator.nextInt(1,5)))

        cart.add(DishesCart(Dish(3,
            "Fries Sandwich",
            7.5f,
            "EGP",
            "Fries sandwich.",
            "https://scontent.fcai20-2.fna.fbcdn.net/v/t1.18169-9/27752038_1985383591684198_833922425111005850_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=730e14&_nc_eui2=AeEiBJ50FRPaJrHZP3l8d5MlYKHJsbte3mFgocmxu17eYVXNvMBBTgcPcUeZUn1r1RCmDsYUwY8EiHoZJqSKPMby&_nc_ohc=uwDzW_GbZyEAX_pa3gx&tn=zBlexu2en-P2Fj5j&_nc_ht=scontent.fcai20-2.fna&oh=00_AfAY9WlF-USCb8zGNXvVaEk4dnO-zCt9TPPSNY-5vg7FAA&oe=63BD46AC"
        ), randomNumberGenerator.nextInt(1,5)))

        cart.add(DishesCart(Dish(4,
            "Pickles",
            3.0f,
            "EGP",
            "Pickles.",
            "https://modo3.com/thumbs/fit630x300/149903/1483863074/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D9%85%D8%AE%D9%84%D9%84%D8%A7%D8%AA_%D9%85%D8%B4%D9%83%D9%84%D8%A9.jpg"
        ), randomNumberGenerator.nextInt(1,5)))

        cart.add(DishesCart(Dish(5,
            "Fool Sandwich",
            5.0f,
            "EGP",
            "Fool sandwich.",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7"
        ), randomNumberGenerator.nextInt(1,5)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        val cartRecyclerView = binding.cartRecyclerView
        val cartRecyclerAdapter = CartRecyclerAdapter(cart)
        cartRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        cartRecyclerView.adapter = cartRecyclerAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalPrice = "Total: ${cart.map { it.dish.price * it.amount }.sum()}"
        binding.totalPrice.text = totalPrice

        binding.addToCartButton.setOnClickListener {
            it.findNavController().navigate(CartFragmentDirections.actionCartFragmentToPaymentMethodFragment(cart.map { view -> view.dish.price * view.amount }.sum()))
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

}