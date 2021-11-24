package com.example.finalproject.utils.constants

/**
 * Константы  Url
 */
object UrlConstants {
    private const val BASE_URL =
        "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"
    const val USD_CURRENCY_PATH = BASE_URL + "usd.json"

    const val NETWORK_ERROR_TAG = "ERROR_NETWORK"
    const val DATABASE_ERROR_TAG = "ERROR_DATABASE"
}