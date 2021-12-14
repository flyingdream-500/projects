package com.example.applebasket.recycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.R
import com.example.applebasket.model.Basket
import kotlinx.android.synthetic.main.apple_layout.view.*

class AppleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(basket: Basket, deleteFunc: (Int) -> Unit) {
        itemView.apple_name.text = basket.appleList.get(adapterPosition).name
        itemView.eat_apple.setOnClickListener {
            deleteFunc(adapterPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup): AppleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.apple_layout, parent, false)
            return AppleViewHolder(view)
        }
    }
}