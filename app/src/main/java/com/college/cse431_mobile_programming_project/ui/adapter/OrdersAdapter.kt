package com.college.cse431_mobile_programming_project.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.OrderRecyclerItem
import com.college.cse431_mobile_programming_project.databinding.OrderCardviewBinding
import com.squareup.picasso.Picasso

class OrdersAdapter(private val orders: ArrayList<OrderRecyclerItem>) : RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private lateinit var currentItem: OrderRecyclerItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentItem = orders[position]
        holder.bind(currentItem.restaurantImgPath, currentItem.restaurantName, currentItem.totalPrice, currentItem.orderId, currentItem.status)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun updateOrdersList(orders: List<OrderRecyclerItem>) {
        this.orders.clear()
        this.orders.addAll(orders)
        notifyDataSetChanged()
    }

    private fun create(parent: ViewGroup): OrdersAdapter.ViewHolder {
        return ViewHolder(
            OrderCardviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class ViewHolder(private val binding: OrderCardviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurantImgPath: String, restaurantName: String, totalPrice: String, orderId: Int, status: String) {
            val orderIdText = "#$orderId"
            binding.orderId.text = orderIdText
            binding.restaurantName.text = restaurantName
            binding.totalPrice.text = totalPrice
            binding.orderStatus.text = status
            Picasso.get().load(restaurantImgPath).into(binding.restaurantImage)
//            binding.restaurantImage.setImageResource(R.drawable.ic_baseline_person_32)
        }

    }

}