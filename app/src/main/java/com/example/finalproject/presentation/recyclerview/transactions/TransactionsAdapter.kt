package com.example.finalproject.presentation.recyclerview.transactions

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.transaction.CurrencyTransaction

/**
 * Адаптер для отображения выполненных валютных операции
 * @see transactionsList - список выполненных транзакции [CurrencyTransaction]
 */
class TransactionsAdapter : RecyclerView.Adapter<TransactionsViewHolder>() {
    private var transactionsList: List<CurrencyTransaction> = emptyList()


    fun setCurrencyList(list: List<CurrencyTransaction>) {
        transactionsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        return TransactionsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        holder.bind(transactionsList[position])
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }
}