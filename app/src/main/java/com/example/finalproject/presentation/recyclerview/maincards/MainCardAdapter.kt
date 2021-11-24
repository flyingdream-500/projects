package com.example.finalproject.presentation.recyclerview.maincards

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.bankcard.BankCard

class MainCardAdapter :  RecyclerView.Adapter<MainCardViewHolder>() {
    private var bankCards: List<BankCard> = arrayListOf()

    fun setBankCards(bankCards: List<BankCard>) {
        this.bankCards = bankCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCardViewHolder {
        return MainCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holderMain: MainCardViewHolder, position: Int) {
        holderMain.bind(bankCards.get(position))
    }

    override fun getItemCount(): Int {
        return bankCards.size
    }
}