package com.example.applebasket.recycler.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.applebasket.model.Apple

class AppleDiffUtil(
    private val oldList: List<Apple>,
    private val newList: List<Apple>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).name == newList.get(newItemPosition).name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListSize == newListSize
    }
}