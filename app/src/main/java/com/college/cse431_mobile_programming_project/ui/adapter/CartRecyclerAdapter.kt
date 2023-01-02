package com.college.cse431_mobile_programming_project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.college.cse431_mobile_programming_project.data.model.DishesCart
import com.college.cse431_mobile_programming_project.databinding.DishCartCardviewBinding
import com.college.cse431_mobile_programming_project.utils.OnQuantityChangeListener
import com.squareup.picasso.Picasso

class CartRecyclerAdapter(private val cart: ArrayList<DishesCart>, quantityChangeListener: OnQuantityChangeListener)
    : RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {

    private lateinit var currentItem: DishesCart
    private var _quantityListener = quantityChangeListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentItem = cart[position]
//        holder.bind(currentItem.dish.name, currentItem.dish.price, currentItem.dish.currency, currentItem.dish.img_path, currentItem.amount)
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    fun remove(position: Int) {
        _quantityListener.onQuantityChange(position, 0)
        cart.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cart.size)
    }

    private fun create(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            DishCartCardviewBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class ViewHolder(private val binding: DishCartCardviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, price: Float, currency: String, img_path: String, amount: Int) {
            var totalPrice = "$currency ${price * amount}"
            val pricePerItem = "($price per item)"
            binding.dishName.text = name
            binding.totalPrice.text = totalPrice
            binding.price.text = pricePerItem
            binding.price.visibility = if (amount > 1) View.VISIBLE else View.GONE
            binding.amount.text = amount.toString().padStart(2, '0')
            Picasso.get().load(img_path).into(binding.dishImage)

            binding.decreaseAmountButton.setOnClickListener {
                var currAmount = binding.amount.text.toString().toInt()
                if (currAmount > 1) {
                    --currAmount
                    _quantityListener.onQuantityChange(absoluteAdapterPosition, currAmount)
                    totalPrice = "$currency ${price * currAmount}"
                    binding.totalPrice.text = totalPrice
                    binding.price.visibility = if (currAmount >= 2) View.VISIBLE else View.GONE
                }
                else {
                    binding.price.visibility = View.GONE
                }
                binding.amount.text = currAmount.toString().padStart(2, '0')
            }

            binding.increaseAmountButton.setOnClickListener {
                var currAmount = binding.amount.text.toString().toInt()
                if (currAmount < 99) {
                    ++currAmount
                    _quantityListener.onQuantityChange(absoluteAdapterPosition, currAmount)
                    totalPrice = "$currency ${price * currAmount}"
                    binding.totalPrice.text = totalPrice
                    binding.price.visibility = View.VISIBLE
                }
                binding.amount.text = currAmount.toString().padStart(2, '0')
            }

            binding.removeFromCart.setOnClickListener {
                remove(this.layoutPosition)
            }
        }
    }
}