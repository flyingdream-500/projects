package com.example.finalproject.domain.usecase

import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.domain.repository.BankCardRepository
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class BankCardsInteractorImpl(
    private val bankCardsRepository: BankCardRepository
) : BankCardsInteractor {

    override fun getBankCards(): Single<List<BankCard>> {
        return bankCardsRepository.getBankCards()
    }

    override fun addBankCard(bankCard: BankCard): Completable {
        return bankCardsRepository.addBankCard(bankCard)
    }

    override fun addBankCards(vararg cards: BankCard): Completable {
        return bankCardsRepository.addBankCards(*cards)
    }

    override fun getBankCardByAbbr(abbreviation: String): Single<BankCard> {
        return bankCardsRepository.getBankCardByAbbr(abbreviation)
    }

}