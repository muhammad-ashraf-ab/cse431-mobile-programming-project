package com.college.cse431_mobile_programming_project.data.recycler_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.Dish
import com.college.cse431_mobile_programming_project.databinding.DishCardviewBinding
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
            currentItem.description,
            currentItem.img_path)
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }

    class ViewHolder(private val binding: DishCardviewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }
        }

        fun bind(name: String, price: Float, currency: String, description: String, img_path: String) {
            val localPrice = "$currency $price"

            binding.dishCardName.text = name
            binding.dishPrice.text = localPrice
            binding.dishCardDescription.text = description
            Picasso.get().load(img_path).into(binding.dishCardImage)
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {

                return ViewHolder(
                    DishCardviewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

}