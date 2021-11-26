package com.example.finalproject.data.db.dao

import androidx.room.*
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.utils.constants.DataBaseConstants.BANK_CARDS_TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


/**
 * Dao с методами взаимодействия с БД банковских карт
 */
@Dao
interface BankCardsDao {

    @Query("SELECT * FROM $BANK_CARDS_TABLE_NAME")
    fun getBankCards(): Single<List<BankCard>>

    @Query("SELECT * FROM $BANK_CARDS_TABLE_NAME WHERE abbreviation = :abbreviation")
    fun getBankCardByAbbr(abbreviation: String): Single<BankCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBankCards(vararg cards: BankCard): Completable


    fun addAndGetCards(vararg cards: BankCard): Single<List<BankCard>> {
        return Single.fromCallable {
            addAndGetCardsSync(*cards)
        }
    }

    /**
     * Синхронные методы добавления и получения обновленных данных, иначе выскакивает ошибка:
     * [Method annotated with @Transaction must not return deferred/async return type io.reactivex.Single.]
     */
    @Transaction
    fun addAndGetCardsSync(vararg cards: BankCard): List<BankCard> {
        addBankCardsSync(*cards)
        return getBankCardsSync()
    }
    @Query("SELECT * FROM $BANK_CARDS_TABLE_NAME")
    fun getBankCardsSync(): List<BankCard>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBankCardsSync(vararg cards: BankCard)


}