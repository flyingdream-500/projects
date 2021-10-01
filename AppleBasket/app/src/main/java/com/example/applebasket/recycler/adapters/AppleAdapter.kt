package com.example.applebasket.recycler.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.model.Basket
import com.example.applebasket.recycler.diffutils.AppleDiffUtil
import com.example.applebasket.recycler.viewholders.AppleViewHolder

class AppleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var basket: Basket = Basket()
    private var deleteFunc: (Int) -> Unit = {}

    fun setDeleteFunc(removeFunc: (Int) -> Unit) {
        deleteFunc = removeFunc
    }

    fun setBasket(bsk: Basket) {
        val diffUtil = AppleDiffUtil(oldList = basket.appleList, newList = bsk.appleList)
        val applesDiffResult = DiffUtil.calculateDiff(diffUtil, true)
        basket.appleList.clear()
        basket.appleList.addAll(bsk.appleList)

        applesDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (basket.appleList.isNotEmpty()) {
            (holder as AppleViewHolder).bind(basket, deleteFunc)
        }
    }

    override fun getItemCount(): Int {
        return basket.appleList.size
    }
}