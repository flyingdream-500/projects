package com.example.finalproject.presentation.recyclerview.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ItemOperationBinding
import com.example.finalproject.model.transaction.CurrencyTransaction


/**
 * View Holder для размещения данных по элементу Recycler View
 */
class TransactionsViewHolder(private val binding: ItemOperationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CurrencyTransaction) {
        Glide.with(itemView)
            .load(item.baseLogo)
            .into(binding.baseLogo)
        Glide.with(itemView)
            .load(item.targetLogo)
            .into(binding.targetLogo)

        binding.baseValue.text = item.baseSum
        binding.targetValue.text = item.targetSum
        binding.date.text = item.date
    }


    companion object {
        fun create(parent: ViewGroup): TransactionsViewHolder {
            val binding = ItemOperationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return TransactionsViewHolder(binding)
        }
    }
}