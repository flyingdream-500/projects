package com.example.applebasket.recycler.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.model.Basket
import com.example.applebasket.recycler.diffutils.BasketDiffUtil
import com.example.applebasket.recycler.viewholders.BasketViewHolder

class BasketAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var baskets: ArrayList<Basket> = arrayListOf()

    fun setBasketList(newBaskets: ArrayList<Basket>) {
        val diffUtil = BasketDiffUtil(oldList = baskets, newList = newBaskets)
        val basketDiffResult = DiffUtil.calculateDiff(diffUtil, true)

        baskets.clear()
        baskets.addAll(newBaskets)
        basketDiffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BasketViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BasketViewHolder).bind(baskets.get(position))
    }

    override fun getItemCount(): Int {
        return baskets.size
    }
}