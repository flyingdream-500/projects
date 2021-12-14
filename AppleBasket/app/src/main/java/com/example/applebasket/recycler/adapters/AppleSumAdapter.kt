package com.example.applebasket.recycler.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.model.Basket
import com.example.applebasket.recycler.viewholders.AppleSumViewHolder

class AppleSumAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var basket: Basket = Basket()

    fun setBasket(newBasket: Basket) {
        basket = newBasket
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppleSumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (basket.appleList.isNotEmpty()) {
            (holder as AppleSumViewHolder).bind(basket)
        }
    }

    override fun getItemCount(): Int {
        return if (basket.appleList.size > 0) 1 else 0
    }
}