package com.example.finalproject.presentation.recyclerview.buyingcards.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.finalproject.model.bankcard.BankCard

/**
 * Diff Util Callback для сравнения списков [BankCard]
 * @param oldList - список старых элементов банковских карт
 * @param newList - список новых элементов банковских карт
 */
class BuyingCardDiffUtils(
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
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return  oldItem.abbreviation == newItem.abbreviation &&
                oldItem.currency == newItem.currency &&
                oldItem.balance == newItem.balance &&
                oldItem.color == newItem.color &&
                oldItem.logo == newItem.logo
    }
}