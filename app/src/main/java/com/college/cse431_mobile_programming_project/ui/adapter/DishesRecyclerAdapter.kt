package com.college.cse431_mobile_programming_project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.databinding.DishRestaurantCardviewBinding
import com.college.cse431_mobile_programming_project.ui.fragments.RestaurantFragmentDirections
import com.squareup.picasso.Picasso

class DishesRecyclerAdapter(private val dishesList: ArrayList<Dish>, private val restaurantId: Int)
    : RecyclerView.Adapter<DishesRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return create(parent, restaurantId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dishesList[position]
        holder.bind(currentItem.name!!,
            currentItem.price!!,
            currentItem.currency!!,
            currentItem.short_description!!,
            currentItem.img_path!!,
            currentItem.available!!)
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }

    fun updateDishesList(dishesList: List<Dish>) {
        this.dishesList.clear()
        this.dishesList.addAll(dishesList)
        notifyDataSetChanged()
    }

    private fun create(parent: ViewGroup, restaurantId: Int): ViewHolder {
        return ViewHolder(
            DishRestaurantCardviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),
            restaurantId
        )
    }

    inner class ViewHolder(private val binding: DishRestaurantCardviewBinding, restaurantId: Int): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (binding.dishUnavailable.visibility == View.INVISIBLE)
                    it.findNavController().navigate(
                        RestaurantFragmentDirections
                            .actionRestaurantFragmentToDishFragment(
                                restaurantId = restaurantId,
                                dishId = dishesList[absoluteAdapterPosition].id
                            )
                    )
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

    }

}