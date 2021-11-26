package com.example.finalproject.data.network.api

import com.example.finalproject.data.network.converter.CurrentCurrencyConverter
import com.example.finalproject.data.network.converter.CurrentCurrencyConverterImpl
import com.example.finalproject.model.network.CurrentCurrency
import com.example.finalproject.utils.constants.UrlConstants.USD_CURRENCY_PATH
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException


/**
 * Конкретная реализация получениия данных о курсе валют
 * с помощью библиотеки OkHttp с наследованием интерфейса [CurrencyApi]
 * Параметры:
 * @param okHttpClient экземпляр класса OkHttp
 * @param converter  экземпляр класса конвертера [CurrentCurrencyConverterImpl] для перевода JSON файла в класс: [CurrentCurrency]
 */
class OkHttpCurrencyApiImpl(
    private val okHttpClient: OkHttpClient,
    private val converter: CurrentCurrencyConverter
) : CurrencyApi {

    override fun getCurrency(): Single<CurrentCurrency> {
        return Single.fromCallable {
            val request = Request.Builder()
                .url(USD_CURRENCY_PATH)
                .build()
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException(response.message)
                val body = response.body!!
                converter.convertToCurrentCurrency(body.string())
            }
        }
    }

}