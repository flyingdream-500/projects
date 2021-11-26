package com.example.finalproject.domain.converter

import com.example.finalproject.R
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.network.CurrentCurrency
import com.example.finalproject.model.network.Rates
import com.example.finalproject.utils.common.Abbreviation.*


/**
 * Конкретная реализация репозитория [CurrentCurrencyItemConverter]
 * по конвертации данных о курсах валют [CurrentCurrency] в класс для отображения [CurrentCurrencyItem]
 */
class CurrentCurrencyItemConverterImpl: CurrentCurrencyItemConverter {

    /**
     * Метод для конвертации [CurrentCurrency] в [CurrentCurrencyItem]
     * @param currentCurrency - класс курсов валют
     */
    override fun convertToCurrentCurrencyItem(currentCurrency: CurrentCurrency) : CurrentCurrencyItem {
        return CurrentCurrencyItem(
            currentCurrency.date,
            convertToCurrency(currentCurrency.usd)
        )
    }

    /**
     * Метод для конвертации списка [Rates] в список [Currency]
     * @param rates - список курсов валют
     */
    override fun convertToCurrency(rates: Rates): List<Currency> {
        return arrayListOf(
            Currency(R.drawable.ic_gb, GBP.name, R.string.gb_fullname, rates.gbp, R.string.gb_character, R.color.violet, R.string.gb_description),
            Currency(R.drawable.ic_er, EUR.name, R.string.er_fullname, rates.eur, R.string.er_character, R.color.dark_blue, R.string.eu_description),
            Currency(R.drawable.ic_ch, CHF.name, R.string.ch_fullname, rates.chf, R.string.ch_character, R.color.dark_brown, R.string.ch_description),
            Currency(R.drawable.ic_ca, CAD.name, R.string.ca_fullname, rates.cad, R.string.ca_character, R.color.purple_500, R.string.ca_description),
            Currency(R.drawable.ic_sg, SGD.name, R.string.sg_fullname, rates.sgd, R.string.sg_character, R.color.teal_700, R.string.sg_description),
            Currency(R.drawable.ic_cn, CNY.name, R.string.cn_fullname, rates.cny, R.string.cn_character, R.color.material_blueish, R.string.cn_description),
            Currency(R.drawable.ic_dk, DKK.name, R.string.dk_fullname, rates.dkk, R.string.dk_character, R.color.material_pink, R.string.dk_description),
            Currency(R.drawable.ic_no, NOK.name, R.string.no_fullname, rates.nok, R.string.no_character, R.color.dark_green, R.string.no_description),
            Currency(R.drawable.ic_se, SEK.name, R.string.se_fullname, rates.sek, R.string.se_character, R.color.dark_orange, R.string.se_description),
            Currency(R.drawable.ic_ru, RUB.name, R.string.ru_fullname, rates.rub, R.string.ru_character, R.color.material_greenish, R.string.ru_description),
            Currency(R.drawable.ic_jp, JPY.name, R.string.jp_fullname, rates.jpy, R.string.jp_character, R.color.material_violet, R.string.jp_description)
        )
    }
}