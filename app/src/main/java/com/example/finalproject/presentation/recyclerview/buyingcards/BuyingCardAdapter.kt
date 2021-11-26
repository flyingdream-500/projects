package com.example.finalproject.presentation.recyclerview.buyingcards

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.presentation.recyclerview.buyingcards.diffutils.BuyingCardDiffUtils

/**
 * Адаптер для отображения банковских карт
 * @see buyingCards - список банковских карт [BankCard]
 */
class BuyingCardAdapter :  RecyclerView.Adapter<BuyingCardViewHolder>() {
    private var buyingCards: List<BankCard> = emptyList()

    fun setBankCards(bankCards: List<BankCard>) {
        val diffCallback = BuyingCardDiffUtils(this.buyingCards, bankCards)
        val diffResult = DiffUtil.calculateDiff(diffCallback,true)
        this.buyingCards = ArrayList(bankCards)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getBankCards() = buyingCards

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyingCardViewHolder {
        return BuyingCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BuyingCardViewHolder, position: Int) {
        holder.bind(buyingCards[position])
    }

    override fun getItemCount(): Int {
        return buyingCards.size
    }


}