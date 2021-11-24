package com.example.finalproject.data.network.api

import com.example.finalproject.model.network.CurrentCurrency
import io.reactivex.rxjava3.core.Single

/**
 * Интерфейс для получения данных о курсах валют c API
 * @see CurrentCurrency
 */
interface CurrencyApi {
    fun getCurrency(): Single<CurrentCurrency>
}