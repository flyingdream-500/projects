package com.example.finalproject.model.currency

import android.os.Parcelable
import androidx.annotation.*
import androidx.room.*
import com.example.finalproject.data.db.typeconverter.CurrencyTypeConverter
import com.example.finalproject.utils.constants.DataBaseConstants.CURRENCY_TABLE_NAME
import kotlinx.parcelize.Parcelize


/**
 *
 * Дата класс модели для отображения валюты в списке.
 *
 * @param date      дата полученных данных
 * @param currencyList  список курсов валют
 */
@Parcelize
@Entity(tableName = CURRENCY_TABLE_NAME)
@TypeConverters(CurrencyTypeConverter::class)
data class CurrentCurrencyItem(
    @PrimaryKey
    val date: String,
    val currencyList: List<Currency>
) : Parcelable


/**
 * Дата класс валюты
 *
 * @param abbreviation      аббривеатура валюты
 * @param fullName          полное название валюты
 * @param rate              курс валюты к доллару США
 * @param sign              символ валюты
 * @param color             цвет для соответствующей банковской карты
 */
@Parcelize
data class Currency(
    @DrawableRes
    val logo: Int,
    val abbreviation: String,
    @StringRes
    val fullName: Int,
    val rate: Float,
    @StringRes
    val sign: Int,
    @ColorRes
    val color: Int,
    @StringRes
    val description: Int
) : Parcelable


