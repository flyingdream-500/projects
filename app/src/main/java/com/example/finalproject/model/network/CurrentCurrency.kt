package com.example.finalproject.model.network

import androidx.room.Embedded
import androidx.room.PrimaryKey

/**
 * Дата класс модели валюты с привязкой к дате.
 *
 * @param date      дата полученных данных о курсах валют
 * @param usd       базовая валюта, в данном случае Доллар США
 */
data class CurrentCurrency(
    val date: String,
    val usd: Rates
)


/**
 * Переменные класса содержат информацию о курсе валют к Доллару США
 */
data class Rates(
    // Euro
    val eur: Float,
    // Canadian dollar
    val cad: Float,
    // Chinese Yuan
    val cny: Float,
    // Singapore dollar
    val sgd: Float,
    // Pound sterling
    val gbp: Float,
    // Danish krone
    val dkk: Float,
    // Norwegian krone
    val nok: Float,
    // Swedish krona
    val sek: Float,
    // Swiss franc
    val chf: Float,
    // Japanese yen
    val jpy: Float,
    // Russian ruble
    val rub: Float
)