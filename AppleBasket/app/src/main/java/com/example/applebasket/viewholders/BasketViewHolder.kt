package com.example.applebasket.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.databinding.BasketLayoutBinding
import com.example.applebasket.model.Basket

class BasketViewHolder(private val binding: BasketLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        basket: Basket?,
        addApple: (Int, Basket, Context) -> Unit,
        removeBasket: (Basket, Int) -> Unit
    ) {
        basket?.let { bsk ->
            binding.basketName.text = bsk.name
            binding.putApple.setOnClickListener {
                addApple.invoke(adapterPosition, bsk, binding.root.context)
            }
            binding.basketName.setOnClickListener {
                removeBasket.invoke(basket, adapterPosition)
            }
        }

    }

    companion object {
        fun create(parent: ViewGroup): BasketViewHolder {
            val binding =
                BasketLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BasketViewHolder(binding)
        }
    }
}