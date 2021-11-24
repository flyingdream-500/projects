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
import com.example.finalproject.utils.constants.DefaultConstants.FragmentBundles.CURRENCY_ITEM_KEY
import com.example.finalproject.utils.extensions.BaseExtensions.scaleTo3Symbols

/**
 * View Holder для размещения данных по элементу Recycler View
 */
class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currency: Currency) {
        binding.apply {
            Glide.with(itemView)
                .load(currency.logo)
                .into(itemLogo)
            itemAbbreviation.text = currency.abbreviation
            itemDescription.text = itemView.resources.getString(currency.fullName)
            itemPrice.text = currency.rate.scaleTo3Symbols().toString()
        }
        itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(CURRENCY_ITEM_KEY, currency)
            it.findNavController().navigate(R.id.currencyDetailScreen, bundle)
        }
    }


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