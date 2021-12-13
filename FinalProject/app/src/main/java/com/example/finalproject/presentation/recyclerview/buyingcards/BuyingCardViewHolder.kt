package com.example.finalproject.presentation.recyclerview.buyingcards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.BankCardTransactionItemBinding
import com.example.finalproject.model.bankcard.BankCard
import java.util.*

/**
 * View Holder для отображения банковских карт
 * @see binding - биндинг класс макета [BankCardTransactionItemBinding]
 */
class BuyingCardViewHolder(private val binding: BankCardTransactionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(bankCard: BankCard) {
        binding.run {
            Glide.with(itemView)
                .load(bankCard.logo)
                .into(cardLogo)

            cardBalance.text = String.format(Locale.ENGLISH, itemView.resources.getString(bankCard.currency), bankCard.balance)
            container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, bankCard.color))
        }

    }

    /**
     * Функция создания экземпляра [BuyingCardViewHolder]
     */
    companion object {
        fun create(parent: ViewGroup): BuyingCardViewHolder {
            val binding =
                BankCardTransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BuyingCardViewHolder(binding)
        }
    }
}