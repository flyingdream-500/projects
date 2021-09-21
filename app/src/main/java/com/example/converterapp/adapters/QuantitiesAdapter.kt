package com.example.converterapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.model.Quantity
import com.example.converterapp.viewholders.QuantityViewHolder

class QuantitiesAdapter(private val quantities: ArrayList<Quantity>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuantityViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as QuantityViewHolder).bind(quantities.get(position))
    }

    override fun getItemCount(): Int {
        return quantities.size
    }
}