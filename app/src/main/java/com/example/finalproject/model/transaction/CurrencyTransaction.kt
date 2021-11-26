package com.example.finalproject.model.transaction

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finalproject.utils.constants.DataBaseConstants.TRANSACTIONS_TABLE_NAME

/**
 * Дата класс модели для отображения валютной операции.
 *
 * @param baseSum       сумма закупочной валюты
 * @param targetSum     сумма купленной валюты
 * @param baseLogo      логотип закупочной валюты
 * @param targetLogo    логотип купленной валюты
 * @param date          дата выполненной операции
 */
@Entity(tableName = TRANSACTIONS_TABLE_NAME)
data class CurrencyTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val baseSum: String,
    val targetSum: String,
    @DrawableRes
    val baseLogo: Int,
    @DrawableRes
    val targetLogo: Int,
    val date: String
)