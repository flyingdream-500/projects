package com.example.finalproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalproject.data.db.dao.BankCardsDao
import com.example.finalproject.data.db.dao.CurrentCurrencyDao
import com.example.finalproject.data.db.dao.TransactionsDao
import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.model.user.User
import com.example.finalproject.utils.constants.DataBaseConstants.DATABASE_VERSION


/**
 * Абстрактный класс БД
 */
@Database(
    entities = [CurrencyTransaction::class, BankCard::class, CurrentCurrencyItem::class, User::class],
    version = DATABASE_VERSION
)
abstract class Database : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
    abstract fun bankCardsDao(): BankCardsDao
    abstract fun currentCurrencyDao(): CurrentCurrencyDao
    abstract fun userDao(): UserDao
}