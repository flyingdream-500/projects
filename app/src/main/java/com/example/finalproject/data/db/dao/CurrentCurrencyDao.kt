package com.example.finalproject.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.utils.constants.DataBaseConstants.CURRENCY_TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface CurrentCurrencyDao {

    @Query("SELECT * FROM $CURRENCY_TABLE_NAME")
    fun getCurrentCurrency(): Maybe<CurrentCurrencyItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrentCurrency(currentCurrencyItem: CurrentCurrencyItem): Completable

}