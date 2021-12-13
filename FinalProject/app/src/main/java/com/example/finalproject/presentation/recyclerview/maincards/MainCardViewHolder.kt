package com.example.finalproject.presentation.recyclerview.maincards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.databinding.MainCardItemBinding
import com.example.finalproject.model.bankcard.BankCard
import java.util.*

/**
 * View Holder для отображения банковских карт
 * @see binding - биндинг класс макета [MainCardItemBinding]
 */
class MainCardViewHolder(private val binding: MainCardItemBinding) :
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
     * Функция создания экземпляра [MainCardViewHolder]
     */
    companion object {
        fun create(parent: ViewGroup): MainCardViewHolder {
            val binding =
                MainCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainCardViewHolder(binding)
        }
    }
}