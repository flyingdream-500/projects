package com.example.finalproject.presentation.recyclerview.bankcards

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.presentation.recyclerview.bankcards.diffutils.BankCardDiffUtils

class BankCardAdapter :  RecyclerView.Adapter<BankCardViewHolder>() {
    private var bankCards: List<BankCard> = emptyList()

    fun setBankCards(bankCards: List<BankCard>) {
        //this.bankCards = bankCards
        //notifyDataSetChanged()
        val diffCallback = BankCardDiffUtils(this.bankCards, bankCards)
        val diffResult = DiffUtil.calculateDiff(diffCallback,true)
        this.bankCards = ArrayList(bankCards)
        diffResult.dispatchUpdatesTo(this)

    }

    fun getBankCards() = bankCards

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankCardViewHolder {
        return BankCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BankCardViewHolder, position: Int) {
        holder.bind(bankCards.get(position))
    }

    override fun getItemCount(): Int {
        return bankCards.size
    }


}