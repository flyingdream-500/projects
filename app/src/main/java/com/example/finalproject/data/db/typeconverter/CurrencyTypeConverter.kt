package com.example.finalproject.data.db.typeconverter

import androidx.room.TypeConverter
import com.example.finalproject.model.currency.Currency
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Конвертер для записи списка валют [Currency] в БД
 */
object CurrencyTypeConverter {

    private lateinit var moshi: Moshi

    fun initialize(moshi: Moshi) {
        CurrencyTypeConverter.moshi = moshi
    }

    private val type = Types.newParameterizedType(List::class.java, Currency::class.java)
    private lateinit var adapter: JsonAdapter<List<Currency>>


    /**
     * Получаем список  [Currency] из JSON
     */
    @TypeConverter
    fun fromString(value: String): List<Currency> {
        adapter = moshi.adapter(type)
        return adapter.fromJson(value).orEmpty()
    }

    /**
     * Конвертируем список [Currency] в JSON
     */
    @TypeConverter
    fun fromCurrentCurrencyItem(item: List<Currency>): String {
        adapter = moshi.adapter(type)
        return adapter.toJson(item)
    }

}