package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.DishType
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.recycler_data.DishTypesRecyclerAdapter
import com.college.cse431_mobile_programming_project.data.recycler_data.RestaurantsRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentMainBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private val restaurantsList = ArrayList<Restaurant>()
    private val dishTypesList = ArrayList<DishType>()
    private val foodDishList = ArrayList<Dish>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dishTypesList.add(DishType(1,
            "Breakfast",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/67612388_2146645052305267_4052419102541611008_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=8bfeb9&_nc_eui2=AeHAYBHASR_6P4uRnbwAuxOctdEPKGXDpWm10Q8oZcOlacXl-ra998YWVwSXAsqeXuE71quHJKW3IcRzLJ0Ih5e_&_nc_ohc=gmRNjxgWBTUAX_V0kPF&_nc_ht=scontent-hbe1-1.xx&oh=00_AfA4WPCTUewRcIU0uvNT_LkKt5RxHBoNzaEPaVnyPZzHGg&oe=63BC8107"))

        dishTypesList.add(DishType(2,
            "Lunch",
            "https://m3rfh.b-cdn.net/wp-content/uploads/2021/05/%D8%A3%D9%81%D9%83%D8%A7%D8%B1-%D9%84%D9%84%D8%B9%D8%B4%D8%A7%D8%A1.jpg"))

        dishTypesList.add(DishType(3,
            "Dinner",
            "https://m3rfh.b-cdn.net/wp-content/uploads/2021/05/%D8%A3%D9%81%D9%83%D8%A7%D8%B1-%D9%84%D9%84%D8%BA%D8%AF%D8%A7%D8%A1.jpg"))

        dishTypesList.add(DishType(4,
            "Fool & Falafel",
            "https://menu22.com/images/57104171_1325972874233946_5601971753078226944_n.jpg"))

        dishTypesList.add(DishType(5,
            "Syrian",
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg"))

        dishTypesList.add(DishType(6,
            "Burger",
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg"))

        dishTypesList.add(DishType(7,
            "Pizza",
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg"))

        dishTypesList.add(DishType(8,
            "Pasta",
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg"))

        dishTypesList.add(DishType(9,
            "Chinese",
            "https://lh3.googleusercontent.com/p/AF1QipOhfMj2gQaFgsnyFnoCxntaexHlsNinpEdxDOQ=s680-w680-h510"))

        dishTypesList.add(DishType(10,
            "Crepe",
            "https://www.supermama.me/system/App/Models/Recipe/images/000/106/376/watermarked/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D9%83%D8%B1%D9%8A%D8%A8-%D8%A8%D8%A7%D9%84%D9%84%D8%AD%D9%85%D8%A9-%D8%A7%D9%84%D9%85%D9%81%D8%B1%D9%88%D9%85%D8%A9.jpg"))

        dishTypesList.add(DishType(11,
            "Fried Chicken",
            "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900"))

        dishTypesList.add(DishType(12,
            "Waffles",
            "https://i.pinimg.com/originals/59/a2/af/59a2afb9c78993013ac259f48ed1b169.jpg"))

        dishTypesList.add(DishType(13,
            "Sandwiches",
            "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg"))

        val foolTags = ArrayList<String>()
        foolTags.add("Breakfast")
        foolTags.add("Fool & Falafel")

        val syrianTags = ArrayList<String>()
        syrianTags.add("Lunch")
        syrianTags.add("Syrian")
        syrianTags.add("Shawerma")

        val burgerTags = ArrayList<String>()
        burgerTags.add("Dinner")
        burgerTags.add("Burger")

        val pizzaTags = ArrayList<String>()
        pizzaTags.add("Lunch")
        pizzaTags.add("Dinner")
        pizzaTags.add("Pizza")
        pizzaTags.add("Feteer")

        val pastaTags = ArrayList<String>()
        pastaTags.add("Lunch")
        pastaTags.add("Dinner")
        pastaTags.add("Pasta")

        val chineseTags = ArrayList<String>()
        chineseTags.add("Lunch")
        chineseTags.add("Dinner")
        chineseTags.add("Chinese")

        val crepeTags = ArrayList<String>()
        crepeTags.add("Lunch")
        crepeTags.add("Crepe")
        crepeTags.add("Meat")
        crepeTags.add("Dessert")

        val friedChickenTags = ArrayList<String>()
        friedChickenTags.add("Lunch")
        friedChickenTags.add("Dinner")
        friedChickenTags.add("Fried Chicken")

        val waffleTags = ArrayList<String>()
        waffleTags.add("Breakfast")
        waffleTags.add("Lunch")
        waffleTags.add("Waffle")
        waffleTags.add("Dessert")

        val sandwichTags = ArrayList<String>()
        sandwichTags.add("Lunch")
        sandwichTags.add("Dinner")
        sandwichTags.add("Meat")
        sandwichTags.add("Sandwiches")

        restaurantsList.add(Restaurant(1,
            "Fool & Falafel Restaurant",
            4.5f,
            1234,
            "https://menu22.com/images/57104171_1325972874233946_5601971753078226944_n.jpg",
            foolTags,
            foodDishList))

        restaurantsList.add(Restaurant(2,
            "Syrian Restaurant",
            4.1f,
            3210,
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg",
            syrianTags,
            foodDishList))

        restaurantsList.add(Restaurant(3,
            "Burger Restaurant",
            4.7f,
            923,
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg",
            burgerTags,
            foodDishList))

        restaurantsList.add(Restaurant(4,
            "Pizza Restaurant",
            3.1f,
            210,
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg",
            pizzaTags,
            foodDishList))

        restaurantsList.add(Restaurant(5,
            "Pasta Restaurant",
            4.1f,
            512,
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg",
            burgerTags,
            foodDishList))

        restaurantsList.add(Restaurant(6,
            "Chinese Restaurant",
            4.2f,
            1024,
            "https://lh3.googleusercontent.com/p/AF1QipOhfMj2gQaFgsnyFnoCxntaexHlsNinpEdxDOQ=s680-w680-h510",
            chineseTags,
            foodDishList))

        restaurantsList.add(Restaurant(7,
            "Crepe Restaurant",
            2.9f,
            310,
            "https://www.supermama.me/system/App/Models/Recipe/images/000/106/376/watermarked/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D9%83%D8%B1%D9%8A%D8%A8-%D8%A8%D8%A7%D9%84%D9%84%D8%AD%D9%85%D8%A9-%D8%A7%D9%84%D9%85%D9%81%D8%B1%D9%88%D9%85%D8%A9.jpg",
            crepeTags,
            foodDishList))

        restaurantsList.add(Restaurant(8,
            "Fried Chicken Restaurant",
            3.9f,
            2312,
            "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900",
            friedChickenTags,
            foodDishList))

        restaurantsList.add(Restaurant(9,
            "Waffles Restaurant",
            4.4f,
            715,
            "https://i.pinimg.com/originals/59/a2/af/59a2afb9c78993013ac259f48ed1b169.jpg",
            waffleTags,
            foodDishList))

        restaurantsList.add(Restaurant(10,
            "Sandwiches Restaurant",
            4.1f,
            4096,
            "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg",
            sandwichTags,
            foodDishList))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

//        database = Firebase.database.reference
//
//        database.child("dishes")

        if (restaurantsList.isNotEmpty()) {
            val restaurantsRecyclerView = binding.restaurantsRecyclerview
            val restaurantsRecyclerAdapter = RestaurantsRecyclerAdapter(restaurantsList)
            restaurantsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            restaurantsRecyclerView.adapter = restaurantsRecyclerAdapter

            if (binding.restaurantsRecyclerview.id == binding.restaurantsViewSwitcher.nextView.id) {
                binding.restaurantsViewSwitcher.showNext()
            }
        }
        else if (binding.noRestaurantsFoundText.id == binding.restaurantsViewSwitcher.nextView.id) {
            binding.restaurantsViewSwitcher.showNext()
        }

        if (dishTypesList.isNotEmpty()) {
            val dishesRecyclerView = binding.dishTypesRecyclerview
            val dishTypesRecyclerAdapter = DishTypesRecyclerAdapter(dishTypesList)
            dishesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            dishesRecyclerView.adapter = dishTypesRecyclerAdapter

            if (binding.dishTypesRecyclerview.id == binding.dishesViewSwitcher.nextView.id) {
                binding.dishesViewSwitcher.showNext()
            }
        }
        else if (binding.noDishesFoundText.id == binding.dishesViewSwitcher.nextView.id) {
            binding.dishesViewSwitcher.showNext()
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