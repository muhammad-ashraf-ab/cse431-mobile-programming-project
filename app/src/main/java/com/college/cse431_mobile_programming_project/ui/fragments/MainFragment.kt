package com.college.cse431_mobile_programming_project.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.data.recycler_data.RestaurantsRecyclerAdapter
import com.college.cse431_mobile_programming_project.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val restaurantsList = ArrayList<Restaurant>()
    private lateinit var _binding : FragmentMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val foolTags = ArrayList<String>()
        foolTags.add("Breakfast")
        foolTags.add("Fool")
        foolTags.add("Falafel")

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

        restaurantsList.add(Restaurant(1,
            "Fool & Falafel Restaurant",
            4.5f,
            1234,
            "https://gumlet.assettype.com/sabq%2F2022-04%2F523bcca7-e1e5-4598-8190-ce702fbe03ea%2F1e30b9a1-b9b4-41b3-a516-fd351b6efde1.jpg?auto=format%2Ccompress&fit=max&format=webp&w=400&dpr=2.6",
            foolTags))

        restaurantsList.add(Restaurant(2,
            "Syrian Restaurant",
            4.1f,
            3210,
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg",
            syrianTags))

        restaurantsList.add(Restaurant(3,
            "Burger Restaurant",
            4.7f,
            923,
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg",
            burgerTags))

        restaurantsList.add(Restaurant(4,
            "Pizza Restaurant",
            3.1f,
            210,
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg",
            pizzaTags))

        restaurantsList.add(Restaurant(5,
            "Pasta Restaurant",
            4.1f,
            512,
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg",
            burgerTags))

        restaurantsList.add(Restaurant(6,
            "Fool & Falafel Restaurant",
            4.5f,
            1234,
            "https://gumlet.assettype.com/sabq%2F2022-04%2F523bcca7-e1e5-4598-8190-ce702fbe03ea%2F1e30b9a1-b9b4-41b3-a516-fd351b6efde1.jpg?auto=format%2Ccompress&fit=max&format=webp&w=400&dpr=2.6",
            foolTags))

        restaurantsList.add(Restaurant(7,
            "Syrian Restaurant",
            4.1f,
            3210,
            "https://www.knoozi.com/wp-content/uploads/2018/02/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9-%D8%B9%D9%85%D9%84-%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%A7%D9%84%D8%AF%D8%AC%D8%A7%D8%AC-%D8%A7%D9%84%D8%B3%D9%88%D8%B1%D9%8A%D8%A9.jpg",
            syrianTags))

        restaurantsList.add(Restaurant(8,
            "Burger Restaurant",
            4.7f,
            923,
            "https://shamlola.s3.amazonaws.com/Shamlola_Images/7/src/7d63e3088cd4f45c20dac8671bb3eea1d98a22c5.jpg",
            burgerTags))

        restaurantsList.add(Restaurant(9,
            "Pizza Restaurant",
            3.1f,
            210,
            "https://modo3.com/thumbs/fit630x300/51334/1435144381/%D8%B7%D8%B1%D9%8A%D9%82%D8%A9_%D8%B9%D9%85%D9%84_%D8%B9%D8%AC%D9%8A%D9%86%D8%A9_%D8%A7%D9%84%D8%A8%D9%8A%D8%AA%D8%B2%D8%A7_%D8%A7%D9%84%D8%A5%D9%8A%D8%B7%D8%A7%D9%84%D9%8A%D8%A9.jpg",
            pizzaTags))

        restaurantsList.add(Restaurant(10,
            "Pasta Restaurant",
            4.1f,
            512,
            "https://www.justfood.tv/nawa3emPics/4bCJFR12.7.2019-1.jpg",
            burgerTags))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val restaurantsRecyclerView = binding.restaurantsRecyclerview
        val restaurantsRecyclerAdapter = RestaurantsRecyclerAdapter(restaurantsList)
        restaurantsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        restaurantsRecyclerView.adapter = restaurantsRecyclerAdapter
        return view
    }
}