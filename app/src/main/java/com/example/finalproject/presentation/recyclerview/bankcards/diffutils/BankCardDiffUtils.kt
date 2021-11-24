package com.example.finalproject.presentation.recyclerview.bankcards.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.finalproject.model.bankcard.BankCard

class BankCardDiffUtils(
    private val oldList: List<BankCard>,
    private val newList: List<BankCard>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).id == newList.get(newItemPosition).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList.get(oldItemPosition)
        val newItem = newList.get(newItemPosition)
        return  oldItem.abbreviation == newItem.abbreviation &&
                oldItem.currency == newItem.currency &&
                oldItem.balance == newItem.balance &&
                oldItem.color == newItem.color &&
                oldItem.logo == newItem.logo
    }
}