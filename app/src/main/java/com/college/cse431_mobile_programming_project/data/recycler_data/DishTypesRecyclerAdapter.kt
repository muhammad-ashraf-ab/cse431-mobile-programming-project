package com.college.cse431_mobile_programming_project.data.recycler_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.DishType
import com.college.cse431_mobile_programming_project.databinding.DishesCardviewBinding
import com.college.cse431_mobile_programming_project.ui.fragments.MainFragmentDirections
import com.squareup.picasso.Picasso

class DishTypesRecyclerAdapter(private val dishesList: ArrayList<DishType>)
    : RecyclerView.Adapter<DishTypesRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dishesList[position]
        holder.bind(currentItem.name, currentItem.img_path)
    }

    override fun getItemCount(): Int {
        return dishesList.size
    }

    class ViewHolder(private val binding: DishesCardviewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val action = MainFragmentDirections
                    .actionMainFragmentToDishTypeFragment(binding.dishCardName.text.toString())
                it.findNavController().navigate(action)
            }
        }

        fun bind(name: String, img_path: String) {
            binding.dishCardName.text = name
            Picasso.get().load(img_path).into(binding.dishCardImage)
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {

                return ViewHolder(
                    DishesCardviewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

}