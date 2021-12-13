package com.example.finalproject.args

import com.example.finalproject.R
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.model.user.User
import com.example.finalproject.utils.common.Abbreviation

/**
 * Дефолтные значения для тестирования
 */

/**
 * Дефолтное значение валютной транзакции
 */
val CURRENT_TRANSACTION_DB = CurrencyTransaction(
    id = 1,
    baseSum = "1000",
    targetSum = "2000",
    baseLogo = R.drawable.ic_us,
    targetLogo = R.drawable.ic_ru,
    date = "2021-11-26"
)

/**
 * Дефолтное значение банковской карты
 */
val DEFAULT_BANK_CARD_DB  = BankCard(
    id = 1,
    balance = 1_000.00,
    logo = R.drawable.ic_us,
    currency = R.string.us_character,
    abbreviation = Abbreviation.USD.name,
    color = R.color.black,
    description = R.string.us_description
)

/**
 * Дефолтное значение данных пользователя
 */
val DEFAULT_USER_DB  = User(
    id = 1,
    name = "Ivan",
    surname = "Ivanov",
    avatar = ""
)

/**
 * Дефолтное значение данных о курсах валют, конвертированных в [CurrentCurrencyItem] для отображения в списке
 */
val CURRENT_CURRENCY_ITEM_DB  = CurrentCurrencyItem(
    date = "2021-11-26",
    listOf(
            Currency(R.drawable.ic_gb, Abbreviation.GBP.name, R.string.gb_fullname, 0.726351f, R.string.gb_character, R.color.violet, R.string.gb_description),
            Currency(R.drawable.ic_er, Abbreviation.EUR.name, R.string.er_fullname, 0.862298f, R.string.er_character, R.color.dark_blue, R.string.eu_description),
            Currency(R.drawable.ic_ch, Abbreviation.CHF.name, R.string.ch_fullname, 0.91976f, R.string.ch_character, R.color.dark_brown, R.string.ch_description),
            Currency(R.drawable.ic_ca, Abbreviation.CAD.name, R.string.ca_fullname, 1.238965f, R.string.ca_character, R.color.purple_500, R.string.ca_description),
            Currency(R.drawable.ic_sg, Abbreviation.SGD.name, R.string.sg_fullname, 1.347226f, R.string.sg_character, R.color.teal_700, R.string.sg_description),
            Currency(R.drawable.ic_cn, Abbreviation.CNY.name, R.string.cn_fullname, 6.38302f, R.string.cn_character, R.color.material_blueish, R.string.cn_description),
            Currency(R.drawable.ic_dk, Abbreviation.DKK.name, R.string.dk_fullname, 6.414402f, R.string.dk_character, R.color.material_pink, R.string.dk_description),
            Currency(R.drawable.ic_no, Abbreviation.NOK.name, R.string.no_fullname, 8.364304f, R.string.no_character, R.color.dark_green, R.string.no_description),
            Currency(R.drawable.ic_se, Abbreviation.SEK.name, R.string.se_fullname, 8.613434f, R.string.se_character, R.color.dark_orange, R.string.se_description),
            Currency(R.drawable.ic_ru, Abbreviation.RUB.name, R.string.ru_fullname, 69.47352f, R.string.ru_character, R.color.material_greenish, R.string.ru_description),
            Currency(R.drawable.ic_jp, Abbreviation.JPY.name, R.string.jp_fullname, 114.18154f, R.string.jp_character, R.color.material_violet, R.string.jp_description)

    )
)
