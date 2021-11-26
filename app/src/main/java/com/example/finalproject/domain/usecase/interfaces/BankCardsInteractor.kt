package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.data.repository.BankCardRepositoryImpl
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.model.bankcard.BankCard
import io.reactivex.rxjava3.core.Single

/**
 * Интерфейс интерактора по работе с банковскими картами
 * Реализация в [BankCardsInteractorImpl], используем [BankCardRepositoryImpl]
 */
interface BankCardsInteractor {

    /**
     * Метод для получения банковских карт
     */
    fun getBankCards(): Single<List<BankCard>>

    /**
     * Метод для получения банковской карты по аббривиатуре валюты из БД
     * @param abbreviation - аббривиатура валюты
     */
    fun getBankCardByAbbr(abbreviation: String): Single<BankCard>

    /**
     * Метод для добавления банковских карт в БД и возвращает обновленные данные
     * @param cards - банковские карты для добавления в БД
     */
    fun addAndGetCards(vararg cards: BankCard): Single<List<BankCard>>
}