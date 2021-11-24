package com.example.finalproject.model.bankcard

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finalproject.utils.constants.DataBaseConstants.BANK_CARDS_TABLE_NAME

@Entity(tableName = BANK_CARDS_TABLE_NAME)
data class BankCard(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var balance: Double,
    @DrawableRes
    val logo: Int,
    @StringRes
    val currency: Int,
    val abbreviation: String,
    @ColorRes
    val color: Int
)