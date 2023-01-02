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
import com.college.cse431_mobile_programming_project.ui.adapter.CartRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentCartBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.college.cse431_mobile_programming_project.utils.OnQuantityChangeListener
import kotlin.random.Random

class CartFragment : Fragment() {

    private var cart = ArrayList<DishesCart>()
    private lateinit var randomNumberGenerator: Random
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        randomNumberGenerator = Random(System.currentTimeMillis())

//        cart.add(DishesCart(Dish(1,
//            "Fool Sandwich",
//            5.0f,
//            "EGP",
//            "Fool sandwich.",
//            "A finely made fool sandwich made of the most exquisite of ingredients. The beans were grown in a farm with water taken directly from melting Antarctican glaciers. The bread was baked in the gates of hell. The tahini was created by crushing real human bones for the most exquisite of tastes. Truly a sandwich to fall in love with.",
//            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7"
//        ), randomNumberGenerator.nextInt(1,5)))
//
//        cart.add(DishesCart(Dish(2,
//            "Falafel Sandwich",
//            6.0f,
//            "EGP",
//            "Falafel sandwich.",
//            "A falafel sandwich like none you had before. Fried in oil extracted from flowers grown on top of Mount Everest, at the perfect temperature calculated using our latest machine learning model which was sent to us from the future, the falafel present will be perfectly tender as you have never tried before. Using the same beans used in our fool sandwiches, it will keep you drooling at all times, even after finishing the sandwich.",
//            "https://pbs.twimg.com/media/ErbexFhXUAAwJLS.jpg"
//        ), randomNumberGenerator.nextInt(1,5)))
//
//        cart.add(DishesCart(Dish(3,
//            "Fries Sandwich",
//            7.5f,
//            "EGP",
//            "Fries sandwich.",
//            "Ever heard of the Irish great famine? Do you know why it happened? Because their potato supply was depleted after a virus swept away their entire crop yield. It also wasn't helped by the fact that England was taxing them with all what's left. Lesson is, potatos can be made so well they cause an entire nation to be over-reliant on it that a single virus can kill millions. Our fries are made with potatos that are as good, if not better.",
//            "https://scontent.fcai20-2.fna.fbcdn.net/v/t1.18169-9/27752038_1985383591684198_833922425111005850_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=730e14&_nc_eui2=AeEiBJ50FRPaJrHZP3l8d5MlYKHJsbte3mFgocmxu17eYVXNvMBBTgcPcUeZUn1r1RCmDsYUwY8EiHoZJqSKPMby&_nc_ohc=uwDzW_GbZyEAX_pa3gx&tn=zBlexu2en-P2Fj5j&_nc_ht=scontent.fcai20-2.fna&oh=00_AfAY9WlF-USCb8zGNXvVaEk4dnO-zCt9TPPSNY-5vg7FAA&oe=63BD46AC"
//        ), randomNumberGenerator.nextInt(1,5)))
//
//        cart.add(DishesCart(Dish(4,
//            "Pickles",
//            3.0f,
//            "EGP",
//            "Pickles.",
//            "Made from vinegar chemically crafted using what was learned in Thanaweya 'Amma organic chemistry, with salts directly extracted from meteorites, these are the perfect pickles. Here is a funny story, a friend of mine absolutely hates pickles but keeps commenting how I don't like cinnamon and how \"it makes no sense\". The double standards are real.",
//            "https://modo3.com/thumbs/fit630x300/149903/1483863074/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D9%85%D8%AE%D9%84%D9%84%D8%A7%D8%AA_%D9%85%D8%B4%D9%83%D9%84%D8%A9.jpg"
//        ), randomNumberGenerator.nextInt(1,5)))

//        cart.add(DishesCart(Dish(5,
//            "Fool Sandwich",
//            5.0f,
//            "EGP",
//            "Fool sandwich.",
//            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7"
//        ), randomNumberGenerator.nextInt(1,5)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        if (cart.isNotEmpty()) {
            val cartRecyclerView = binding.cartRecyclerView
            val cartRecyclerAdapter = CartRecyclerAdapter(cart, object : OnQuantityChangeListener {
                override fun onQuantityChange(position: Int, amount: Int) {
                    cart[position].amount = amount
                    val totalPrice = getCartTotalText()
                    binding.totalPrice.text = totalPrice
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


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalPrice = getCartTotalText()
        binding.totalPrice.text = totalPrice

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