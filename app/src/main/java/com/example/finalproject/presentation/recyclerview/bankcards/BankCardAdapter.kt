package com.example.finalproject.presentation.recyclerview.bankcards

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.bankcard.BankCard

/**
 * Адаптер для отображения банковских карт
 * @see bankCards - список банковских карт [BankCard]
 */
class BankCardAdapter : RecyclerView.Adapter<BankCardViewHolder>() {
    private var bankCards: List<BankCard> = emptyList()

    fun setBankCards(bankCards: List<BankCard>) {
        this.bankCards = bankCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankCardViewHolder {
        return BankCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BankCardViewHolder, position: Int) {
        holder.bind(bankCards[position])
    }

    override fun getItemCount(): Int {
        return bankCards.size
    }
}