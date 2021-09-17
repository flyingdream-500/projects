package com.example.applebasket.recycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.R
import com.example.applebasket.model.Basket
import kotlinx.android.synthetic.main.apple_sum_layout.view.*

class AppleSumViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun  bind(basket: Basket) {
        itemView.apples_sum.text = String.format(itemView.resources.getString(R.string.apple_sum), basket.appleList.size)
    }

    companion object {
        fun create(parent: ViewGroup): AppleSumViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.apple_sum_layout, parent, false)
            return AppleSumViewHolder(view)
        }
    }
}