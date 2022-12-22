package com.college.cse431_mobile_programming_project.data.recycler_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.databinding.DishRestaurantCardviewBinding
import com.college.cse431_mobile_programming_project.ui.fragments.RestaurantFragmentDirections
import com.squareup.picasso.Picasso

class DishesRecyclerAdapter(private val dishesList: ArrayList<Dish>)
    : RecyclerView.Adapter<DishesRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dishesList[position]
        holder.bind(currentItem.name,
            currentItem.price,
            currentItem.currency,
            currentItem.short_description,
            currentItem.img_path,
            currentItem.available)
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }

    class ViewHolder(private val binding: DishRestaurantCardviewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (binding.dishUnavailable.visibility == View.INVISIBLE)
                    it.findNavController().navigate(RestaurantFragmentDirections.actionRestaurantFragmentToDishFragment(binding.dishCardName.text.toString()))
            }
        }

        fun bind(name: String, price: Float, currency: String, description: String, img_path: String, available: Boolean) {
            val localPrice = "$currency $price"

            binding.dishCardName.text = name
            binding.dishPrice.text = localPrice
            binding.dishCardDescription.text = description
            Picasso.get().load(img_path).into(binding.dishCardImage)

            binding.dishUnavailable.visibility = if (available) View.INVISIBLE else View.VISIBLE
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {

                return ViewHolder(
                    DishRestaurantCardviewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

}