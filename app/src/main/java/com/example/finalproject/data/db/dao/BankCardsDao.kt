package com.example.finalproject.data.db.dao

import androidx.room.*
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.utils.constants.DataBaseConstants.BANK_CARDS_TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface BankCardsDao {

    @Query("SELECT * FROM $BANK_CARDS_TABLE_NAME")
    fun getBankCards(): Single<List<BankCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBankCard(bankCard: BankCard): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBankCards(vararg cards: BankCard): Completable

    @Query("SELECT * FROM $BANK_CARDS_TABLE_NAME WHERE abbreviation = :abbreviation")
    fun getBankCardByAbbr(abbreviation: String): Single<BankCard>

}