package com.example.finalproject.domain.repository

import com.example.finalproject.model.bankcard.BankCard
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface BankCardRepository {

    fun getBankCards(): Single<List<BankCard>>

    fun addBankCard(bankCard: BankCard): Completable

    fun addBankCards(vararg cards: BankCard): Completable

    fun getBankCardByAbbr(abbreviation: String): Single<BankCard>

}