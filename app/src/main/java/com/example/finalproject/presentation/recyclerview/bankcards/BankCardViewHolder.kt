package com.example.finalproject.presentation.recyclerview.bankcards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.ItemBankCardBinding
import com.example.finalproject.model.bankcard.BankCard
import java.util.*


/**
 * View Holder для отображения банковских карт
 * @see binding - биндинг класс макета [ItemBankCardBinding]
 */
class BankCardViewHolder(private val binding: ItemBankCardBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(bankCard: BankCard) {

        binding.card.cardBalance.text = String.format(
            Locale.ENGLISH,
            itemView.resources.getString(bankCard.currency),
            bankCard.balance
        )
        binding.card.container.setCardBackgroundColor(
            ContextCompat.getColor(
                itemView.context,
                bankCard.color
            )
        )

        Glide.with(itemView)
            .load(bankCard.logo)
            .into(binding.card.cardLogo)

        binding.cardDescription.text = itemView.resources.getString(bankCard.description)
    }


    /**
     * Функция создания экземпляра [BankCardViewHolder]
     */
    companion object {
        fun create(parent: ViewGroup): BankCardViewHolder {
            val binding =
                ItemBankCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BankCardViewHolder(binding)
        }
    }
}