package com.example.finalproject.presentation.recyclerview.currency

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.currency.Currency

/**
 * Адаптер для отображения элементов списка.
 */
class CurrencyAdapter : RecyclerView.Adapter<CurrencyViewHolder>() {
    private var currentCurrencyItemList: List<Currency> = emptyList()

    fun setCurrency(currentCurrencyItemList: List<Currency>) {
        this.currentCurrencyItemList = currentCurrencyItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(currentCurrencyItemList.get(position))
    }

    override fun getItemCount(): Int {
        return currentCurrencyItemList.size
    }
}