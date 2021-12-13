package com.example.finalproject.presentation.recyclerview.maincards

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.bankcard.BankCard

/**
 * Адаптер для отображения банковских карт
 * @see mainCards - список банковских карт [BankCard]
 */
class MainCardAdapter :  RecyclerView.Adapter<MainCardViewHolder>() {
    private var mainCards: List<BankCard> = emptyList()

    fun setBankCards(bankCards: List<BankCard>) {
        this.mainCards = bankCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCardViewHolder {
        return MainCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holderMain: MainCardViewHolder, position: Int) {
        holderMain.bind(mainCards[position])
    }

    override fun getItemCount(): Int {
        return mainCards.size
    }
}