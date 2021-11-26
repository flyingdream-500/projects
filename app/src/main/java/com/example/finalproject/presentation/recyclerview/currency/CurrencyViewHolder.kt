package com.example.finalproject.presentation.recyclerview.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.ItemCurrencyBinding
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.utils.constants.FragmentConstants.CURRENCY_ITEM_KEY
import com.example.finalproject.utils.extensions.BaseExtensions.scaleTo3Symbols

/**
 * View Holder для отображения курсов валют
 * @see binding - биндинг класс макета [ItemCurrencyBinding]
 */
class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currency: Currency) {
        binding.run {
            Glide.with(itemView)
                .load(currency.logo)
                .into(itemLogo)

            itemAbbreviation.text = currency.abbreviation
            itemDescription.text = itemView.resources.getString(currency.fullName)
            itemPrice.text = currency.rate.scaleTo3Symbols().toString()
        }

        //переход по клику на эран покупки валюты
        itemView.setOnClickListener { view ->
            val bundle = Bundle().also {
                it.putParcelable(CURRENCY_ITEM_KEY, currency)
            }
            view.findNavController().navigate(R.id.currencyDetailScreen, bundle)
        }
    }

    /**
     * Функция создания экземпляра [CurrencyViewHolder]
     */
    companion object {
        fun create(viewGroup: ViewGroup): CurrencyViewHolder {
            val binding = ItemCurrencyBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
            return CurrencyViewHolder(binding)
        }
    }

}