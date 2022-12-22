package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.recycler_data.RestaurantsRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentDishTypeBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity

class DishTypeFragment : Fragment() {

    private val allRestaurantsList = ArrayList<Restaurant>()
    private val restaurantsList = ArrayList<Restaurant>()
    private val foodDishList = ArrayList<Dish>()
    private var _binding: FragmentDishTypeBinding? = null
    private val binding get() = _binding!!
    val args: DishTypeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        allRestaurantsList.add(
            Restaurant(1,
            "Fool & Falafel Restaurant",
            4.5f,
            1234,
            "https://menu22.com/images/57104171_1325972874233946_5601971753078226944_n.jpg",
            foolTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(2,
            "Syrian Restaurant",
            4.1f,
            3210,
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg",
            syrianTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(3,
            "Burger Restaurant",
            4.7f,
            923,
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg",
            burgerTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(4,
            "Pizza Restaurant",
            3.1f,
            210,
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg",
            pizzaTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(5,
            "Pasta Restaurant",
            4.1f,
            512,
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg",
            burgerTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(6,
            "Chinese Restaurant",
            4.2f,
            1024,
            "https://lh3.googleusercontent.com/p/AF1QipOhfMj2gQaFgsnyFnoCxntaexHlsNinpEdxDOQ=s680-w680-h510",
            chineseTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(7,
            "Crepe Restaurant",
            2.9f,
            310,
            "https://www.supermama.me/system/App/Models/Recipe/images/000/106/376/watermarked/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D9%83%D8%B1%D9%8A%D8%A8-%D8%A8%D8%A7%D9%84%D9%84%D8%AD%D9%85%D8%A9-%D8%A7%D9%84%D9%85%D9%81%D8%B1%D9%88%D9%85%D8%A9.jpg",
            crepeTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(8,
            "Fried Chicken Restaurant",
            3.9f,
            2312,
            "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900",
            friedChickenTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(9,
            "Waffles Restaurant",
            4.4f,
            715,
            "https://i.pinimg.com/originals/59/a2/af/59a2afb9c78993013ac259f48ed1b169.jpg",
            waffleTags,
            foodDishList)
        )

        allRestaurantsList.add(
            Restaurant(10,
            "Sandwiches Restaurant",
            4.1f,
            4096,
            "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg",
            sandwichTags,
            foodDishList)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishTypeBinding.inflate(inflater, container, false)
        val view = binding.root

        allRestaurantsList.forEach {
            if (args.tag in it.tags)
                restaurantsList.add(it)
        }

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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