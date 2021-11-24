package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.BankCardsDao
import com.example.finalproject.domain.repository.BankCardRepository
import com.example.finalproject.model.bankcard.BankCard
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class BankCardRepositoryImpl(
    private val bankCardsDao: BankCardsDao
) : BankCardRepository {

    override fun getBankCards(): Single<List<BankCard>> {
        return bankCardsDao.getBankCards()
    }

    override fun addBankCard(bankCard: BankCard): Completable {
        return bankCardsDao.addBankCard(bankCard)
    }

    override fun addBankCards(vararg cards: BankCard): Completable {
        return bankCardsDao.addBankCards(*cards)
    }

    override fun getBankCardByAbbr(abbreviation: String): Single<BankCard> {
        return bankCardsDao.getBankCardByAbbr(abbreviation)
    }
}