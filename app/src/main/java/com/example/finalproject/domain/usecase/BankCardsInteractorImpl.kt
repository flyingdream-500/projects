package com.example.finalproject.domain.usecase

import com.example.finalproject.data.repository.BankCardRepositoryImpl
import com.example.finalproject.domain.repository.BankCardRepository
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.model.bankcard.BankCard
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация интерфейса [BankCardsInteractor] по работе с банковскими картами
 * Параметры:
 * @param bankCardsRepository репозиторий по работе с банковскими картами, реализован в классе [BankCardRepositoryImpl]
 */
class BankCardsInteractorImpl(
    private val bankCardsRepository: BankCardRepository
) : BankCardsInteractor {

    /**
     * Метод для получения банковских карт
     */
    override fun getBankCards(): Single<List<BankCard>> {
        return bankCardsRepository.getBankCards()
    }

    /**
     * Метод для получения банковской карты по аббривиатуре валюты из БД
     * @param abbreviation - аббривиатура валюты
     */
    override fun getBankCardByAbbr(abbreviation: String): Single<BankCard> {
        return bankCardsRepository.getBankCardByAbbr(abbreviation)
    }

    /**
     * Метод для добавления банковских карт в БД и возвращает обновленные данные
     * @param cards - банковские карты для добавления в БД
     */
    override fun addAndGetCards(vararg cards: BankCard): Single<List<BankCard>> {
        return bankCardsRepository.addAndGetCards(*cards)
    }

}