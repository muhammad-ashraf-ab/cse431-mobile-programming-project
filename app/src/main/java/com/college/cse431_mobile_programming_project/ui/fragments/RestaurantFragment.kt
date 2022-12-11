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
import com.college.cse431_mobile_programming_project.data.recycler_data.DishesRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentRestaurantBinding
import com.college.cse431_mobile_programming_project.ui.MainActivity

class RestaurantFragment : Fragment() {

    private val foolList = ArrayList<Dish>()
    private val allRestaurantsList = ArrayList<Restaurant>()
    private var dishesList = ArrayList<Dish>()
    private var _binding: FragmentRestaurantBinding? = null
    private val binding get() = _binding!!
    private val args: RestaurantFragmentArgs by navArgs()

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

        foolList

        foolList.add(Dish(1,
            "Fool Sandwich",
            5.0f,
            "EGP",
            "Fool sandwich.",
            "https://scontent-hbe1-1.xx.fbcdn.net/v/t1.6435-9/60127232_1012635668946457_170317722691829760_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=cdbe9c&_nc_eui2=AeFdLYgFf3GwwPiugcfP2t_xF1JFxbvQ96IXUkXFu9D3oma9HP71DkIlRy3rjf41Ssm9Z6dIKtPdki6kiCnZyw4Z&_nc_ohc=fgF6lROFYVgAX8GvVY-&_nc_ht=scontent-hbe1-1.xx&oh=00_AfDwtOOLmna3V4AHN1_u_wJRtfEdQTw1c60twtuXcBBuDA&oe=63BD4CF7"))
        foolList.add(Dish(2,
            "Falafel Sandwich",
            6.0f,
            "EGP",
            "Falafel sandwich.",
            "https://pbs.twimg.com/media/ErbexFhXUAAwJLS.jpg"))
        foolList.add(Dish(3,
            "Fries Sandwich",
            7.5f,
            "EGP",
            "Fries sandwich.",
            "https://scontent.fcai20-2.fna.fbcdn.net/v/t1.18169-9/27752038_1985383591684198_833922425111005850_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=730e14&_nc_eui2=AeEiBJ50FRPaJrHZP3l8d5MlYKHJsbte3mFgocmxu17eYVXNvMBBTgcPcUeZUn1r1RCmDsYUwY8EiHoZJqSKPMby&_nc_ohc=uwDzW_GbZyEAX_pa3gx&tn=zBlexu2en-P2Fj5j&_nc_ht=scontent.fcai20-2.fna&oh=00_AfAY9WlF-USCb8zGNXvVaEk4dnO-zCt9TPPSNY-5vg7FAA&oe=63BD46AC"))
        foolList.add(Dish(4,
            "Pickles",
            3.0f,
            "EGP",
            "Pickles.",
            "https://modo3.com/thumbs/fit630x300/149903/1483863074/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D9%85%D8%AE%D9%84%D9%84%D8%A7%D8%AA_%D9%85%D8%B4%D9%83%D9%84%D8%A9.jpg"))

        allRestaurantsList.add(
            Restaurant(1,
                "Fool & Falafel Restaurant",
                4.5f,
                1234,
                "https://menu22.com/images/57104171_1325972874233946_5601971753078226944_n.jpg",
                foolTags,
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(2,
                "Syrian Restaurant",
                4.1f,
                3210,
                "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg",
                syrianTags,
//                syrianList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(3,
                "Burger Restaurant",
                4.7f,
                923,
                "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg",
                burgerTags,
//                burgerList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(4,
                "Pizza Restaurant",
                3.1f,
                210,
                "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg",
                pizzaTags,
//                pizzaList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(5,
                "Pasta Restaurant",
                4.1f,
                512,
                "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg",
                burgerTags,
//                pastaList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(6,
                "Chinese Restaurant",
                4.2f,
                1024,
                "https://lh3.googleusercontent.com/p/AF1QipOhfMj2gQaFgsnyFnoCxntaexHlsNinpEdxDOQ=s680-w680-h510",
                chineseTags,
//                chineseList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(7,
                "Crepe Restaurant",
                2.9f,
                310,
                "https://www.supermama.me/system/App/Models/Recipe/images/000/106/376/watermarked/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D9%83%D8%B1%D9%8A%D8%A8-%D8%A8%D8%A7%D9%84%D9%84%D8%AD%D9%85%D8%A9-%D8%A7%D9%84%D9%85%D9%81%D8%B1%D9%88%D9%85%D8%A9.jpg",
                crepeTags,
//                crepeList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(8,
                "Fried Chicken Restaurant",
                3.9f,
                2312,
                "https://static.toiimg.com/thumb/61589069.cms?width=1200&height=900",
                friedChickenTags,
//                friedChickenList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(9,
                "Waffles Restaurant",
                4.4f,
                715,
                "https://i.pinimg.com/originals/59/a2/af/59a2afb9c78993013ac259f48ed1b169.jpg",
                waffleTags,
//                waffleList)
                foolList)
        )

        allRestaurantsList.add(
            Restaurant(10,
                "Sandwiches Restaurant",
                4.1f,
                4096,
                "https://lh3.googleusercontent.com/-O9hmerSSvpQ/X_8-Z6UFe0I/AAAAAAAACMI/C1NVYgumLIoiseK5Iek2bEuSQ-nC2tqGACLcBGAsYHQ/s16000/%25D8%25B3%25D8%25A7%25D9%2586%25D8%25AF%25D9%2588%25D8%25AA%25D8%25B4%25D8%25A7%25D8%25AA%2B%25D8%25A7%25D9%2584%25D8%25B4%25D9%258A%25D8%25B4%2B%25D8%25B7%25D8%25A7%25D9%2588%25D9%2588%25D9%2582.jpg",
                sandwichTags,
//                sandwichList)
                foolList)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        val view = binding.root

        allRestaurantsList.forEach {
            if (args.restaurantName == it.name) {
                dishesList = it.dishes
                return@forEach
            }
        }

        val dishesRecyclerView = binding.dishesRecyclerview
        val dishesRecyclerAdapter = DishesRecyclerAdapter(dishesList)
        dishesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dishesRecyclerView.adapter = dishesRecyclerAdapter
        return view
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).configureBars(args.restaurantName, true, View.GONE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}