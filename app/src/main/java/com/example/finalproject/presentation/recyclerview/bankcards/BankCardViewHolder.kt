package com.example.finalproject.presentation.recyclerview.bankcards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.BankCardTransactionItemBinding
import com.example.finalproject.model.bankcard.BankCard
import java.util.*

class BankCardViewHolder(private val binding: BankCardTransactionItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(bankCard: BankCard) {
        binding.cardBalance.text = String.format(Locale.ENGLISH, itemView.resources.getString(bankCard.currency), bankCard.balance)
        binding.container.setCardBackgroundColor(ContextCompat.getColor(itemView.context, bankCard.color))
        Glide.with(itemView)
            .load(bankCard.logo)
            .into(binding.cardLogo)

    }

    companion object {
        fun create(parent: ViewGroup): BankCardViewHolder {
            val binding =
                BankCardTransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BankCardViewHolder(binding)
        }
    }
}