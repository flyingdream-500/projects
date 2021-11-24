package com.example.finalproject.presentation.recyclerview.transactions

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.transaction.CurrencyTransaction

/**
 * Адаптер для отображения элементов списка.
 */
class TransactionsAdapter : RecyclerView.Adapter<TransactionsViewHolder>() {
    private var transactionsList: List<CurrencyTransaction> = arrayListOf()

    fun setCurrencyList(list: List<CurrencyTransaction>) {
        transactionsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(transactionsList.get(position))
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }
}