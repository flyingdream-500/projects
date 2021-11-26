package com.example.finalproject.data.network.api

import com.example.finalproject.model.network.CurrentCurrency
import io.reactivex.rxjava3.core.Single

/**
 * Интерфейс для получения данных о курсах валют c API (https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json)
 * @see CurrentCurrency - дата класс, содержащий список курсов валют и дату ролучения данных
 */
interface CurrencyApi {
    fun getCurrency(): Single<CurrentCurrency>
}