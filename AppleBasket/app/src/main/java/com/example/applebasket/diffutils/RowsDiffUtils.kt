package com.example.applebasket.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.applebasket.model.Apple
import com.example.applebasket.model.ApplesSum
import com.example.applebasket.model.Basket
import com.example.applebasket.model.RowType

class RowsDiffUtils(
    private val oldList: List<RowType>,
    private val newList: List<RowType>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList.get(oldItemPosition)
        val newItem = newList.get(newItemPosition)
        return oldItem.rowType == newItem.rowType
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList.get(oldItemPosition)
        val newItem = newList.get(newItemPosition)
        if (oldItem is Basket && newItem is Basket) {
            return oldItem.name == newItem.name && oldItem.apples.size == newItem.apples.size
        }
        if (oldItem is Apple && newItem is Apple) {
            return oldItem.name == newItem.name && oldItem.owner == newItem.owner
        }
        if (oldItem is ApplesSum && newItem is ApplesSum) {
            return oldItem.sum == newItem.sum
        }
        return false
    }
}