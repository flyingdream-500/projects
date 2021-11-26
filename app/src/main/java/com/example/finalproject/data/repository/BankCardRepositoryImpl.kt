package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.BankCardsDao
import com.example.finalproject.domain.repository.BankCardRepository
import com.example.finalproject.model.bankcard.BankCard
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация интерфейса [BankCardRepository] по работе с получением банковских карт
 * Параметры:
 * @param bankCardsDao - Dao для сохранения и получения данных о банковских картах из БД [BankCardsDao]
 */
class BankCardRepositoryImpl(
    private val bankCardsDao: BankCardsDao
) : BankCardRepository {

    /**
     * Метод для получения банковских карт из БД
     */
    override fun getBankCards(): Single<List<BankCard>> {
        return bankCardsDao.getBankCards()
    }

    /**
     * Метод для получения банковской карты по аббривиатуре валюты из БД
     * @param abbreviation - аббривиатура валюты
     */
    override fun getBankCardByAbbr(abbreviation: String): Single<BankCard> {
        return bankCardsDao.getBankCardByAbbr(abbreviation)
    }

    /**
     * Метод для добавления банковских карт в БД и обновления
     * @param cards - банковские карты для добавления в БД
     */
    override fun addAndGetCards(vararg cards: BankCard): Single<List<BankCard>> {
        return bankCardsDao.addAndGetCards(*cards)
    }
}