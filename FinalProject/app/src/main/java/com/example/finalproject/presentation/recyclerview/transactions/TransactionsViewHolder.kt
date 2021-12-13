package com.example.finalproject.presentation.recyclerview.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ItemOperationBinding
import com.example.finalproject.model.transaction.CurrencyTransaction


/**
 * View Holder для отображения выполненных валютных операции
 * @see binding - биндинг класс макета [ItemOperationBinding]
 */
class TransactionsViewHolder(private val binding: ItemOperationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CurrencyTransaction) {
        //показывем базовый логотип
        Glide.with(itemView)
            .load(item.baseLogo)
            .into(binding.baseLogo)
        //показывем целевой логотип
        Glide.with(itemView)
            .load(item.targetLogo)
            .into(binding.targetLogo)

        binding.run {
            baseValue.text = item.baseSum
            targetValue.text = item.targetSum
            date.text = item.date
        }
    }

    /**
     * Функция создания экземпляра [TransactionsViewHolder]
     */
    companion object {
        fun create(parent: ViewGroup): TransactionsViewHolder {
            val binding = ItemOperationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return TransactionsViewHolder(binding)
        }
    }
}