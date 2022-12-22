package com.college.cse431_mobile_programming_project.data.recycler_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.Restaurant
import com.college.cse431_mobile_programming_project.databinding.RestaurantsCardviewBinding
import com.college.cse431_mobile_programming_project.ui.fragments.DishTypeFragmentDirections
import com.college.cse431_mobile_programming_project.ui.fragments.MainFragmentDirections
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class RestaurantsRecyclerAdapter(private val restaurantsList: ArrayList<Restaurant>)
    : RecyclerView.Adapter<RestaurantsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = restaurantsList[position]
        holder.bind(currentItem.name,
            currentItem.rating,
            currentItem.reviewers,
            currentItem.img_path,
            currentItem.tags)
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    fun updateRestaurantsList(restaurantsList : List<Restaurant>) {
        this.restaurantsList.clear()
        this.restaurantsList.addAll(restaurantsList)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RestaurantsCardviewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val restaurantName = binding.restaurantCardName.text.toString()
                val navController = it.findNavController()
                when (it.findNavController().currentDestination!!.label) {
                    "fragment_main" -> navController.navigate(MainFragmentDirections.actionMainFragmentToRestaurantFragment(restaurantName))
                    "fragment_dish_type" -> navController.navigate(DishTypeFragmentDirections.actionDishTypeFragmentToRestaurantFragment(restaurantName))
                }
            }
        }

        fun bind(name: String, rating: Float, reviewers: Int, img_path: String, tags: ArrayList<String>) {
            val reviewsText = "$rating (${prettyCount(reviewers)})"
            var tagsText = ""
            tags.forEach { tagsText += "$it, " }
            tagsText = tagsText.dropLast(2)

            binding.restaurantCardName.text = name
            binding.restaurantCardRatingValue.text = reviewsText
            binding.restaurantCardRating.progress = (rating * 10).toInt()
            binding.restaurantCardTags.text = tagsText
            Picasso.get().load(img_path).into(binding.restaurantCardImage)
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {

                return ViewHolder(RestaurantsCardviewBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }

        private fun prettyCount(number: Number): String {
            val suffix = charArrayOf(' ', 'k', 'M')
            val numValue = number.toLong()
            val value = floor(log10(numValue.toDouble())).toInt()
            val base = value / 3
            return if (value >= 3 && base < suffix.size) {
                DecimalFormat("#0.0").format(
                    numValue / 10.0.pow((base * 3).toDouble())
                ) + suffix[base]
            } else {
                DecimalFormat("#,##0").format(numValue)
            }
        }
    }
}