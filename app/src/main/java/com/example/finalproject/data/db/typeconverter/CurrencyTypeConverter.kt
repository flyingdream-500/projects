package com.example.finalproject.data.db.typeconverter

import androidx.room.TypeConverter
import com.example.finalproject.model.currency.Currency
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object CurrencyTypeConverter {

    private lateinit var moshi: Moshi

    fun initialize(moshi: Moshi) {
        CurrencyTypeConverter.moshi = moshi
    }

    private val type = Types.newParameterizedType(List::class.java, Currency::class.java)
    private lateinit var adapter: JsonAdapter<List<Currency>>

    @TypeConverter
    fun fromString(value: String): List<Currency> {
        adapter = moshi.adapter<List<Currency>>(type)
        return adapter.fromJson(value).orEmpty()
    }

    @TypeConverter
    fun fromCurrentCurrencyItem(item: List<Currency>): String {
        adapter = moshi.adapter<List<Currency>>(type)
        return adapter.toJson(item)
    }

}