package com.example.converterapp.viewholders

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.converterapp.UnitsActivity
import com.example.converterapp.databinding.QuantityItemBinding
import com.example.converterapp.func.Const
import com.example.converterapp.func.Const.QUANTITY_KEY
import com.example.converterapp.model.Quantity

class QuantityViewHolder(private val binding: QuantityItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * Отображение названия величины, его иконки, и обработка клика по view
     */
    fun bind(quantity: Quantity?) {
        if (quantity != null) {
            binding.tvQuantityName.text = itemView.resources.getString(quantity.label)
            Glide.with(itemView.context)
                .load(quantity.iconLabel)
                .into(binding.ivQuantityIcon)

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, UnitsActivity::class.java)
                intent.putExtra(QUANTITY_KEY, quantity)
                itemView.context.startActivity(intent)
            }
        }
    }


    companion object {
        fun create(parent: ViewGroup): QuantityViewHolder {
            val quantityItemBinding =
                QuantityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return QuantityViewHolder(quantityItemBinding)
        }
    }
}