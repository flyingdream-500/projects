package com.example.finalproject.di

import com.example.finalproject.data.network.api.CurrencyApi
import com.example.finalproject.data.network.api.OkHttpCurrencyApiImpl
import com.example.finalproject.data.network.converter.CurrentCurrencyConverter
import com.example.finalproject.data.network.converter.CurrentCurrencyConverterImpl
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverter
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverterImpl
import com.example.finalproject.data.repository.*
import com.example.finalproject.domain.repository.*
import com.example.finalproject.domain.usecase.*
import com.example.finalproject.domain.usecase.interfaces.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Модуль биндинга
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {

    @Binds
    abstract fun bindCurrencyApiImpl_to_CurrencyApi(okHttpApi: OkHttpCurrencyApiImpl): CurrencyApi

    @Binds
    abstract fun bindCurrentCurrencyItemConverter_to_CurrentCurrencyItemConverterImpl(
        currentCurrencyItemConverterImpl: CurrentCurrencyItemConverterImpl
    ): CurrentCurrencyItemConverter

    @Binds
    abstract fun bindCurrentCurrencyConverter_to_CurrentCurrencyConverterImpl(
        currentCurrencyConverterImpl: CurrentCurrencyConverterImpl
    ): CurrentCurrencyConverter
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {
    @Binds
    abstract fun bindTransactionsRepositoryImpl_to_TransactionsRepository(transactionsRepositoryImpl: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    abstract fun bindCurrencyRepositoryImpl_to_CurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository

    @Binds
    abstract fun bindUserRepositoryImpl_to_UserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindBankCardRepositoryImpl_to_BankCardRepository(bankCardRepositoryImpl: BankCardRepositoryImpl): BankCardRepository

    @Binds
    abstract fun bindSettingsRepositoryImpl_to_SettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorBindModule {
    @Binds
    abstract fun bindTransactionInteractorImpl_to_TransactionInteractor(transactionsInteractorImpl: TransactionsInteractorImpl): TransactionInteractor

    @Binds
    abstract fun bindCurrencyInteractorImpl_to_CurrencyInteractor(currencyInteractorImpl: CurrencyInteractorImpl): CurrencyInteractor


    @Binds
    abstract fun bindUserInteractorImpl_to_UserInteractor(userInteractorImpl: UserInteractorImpl): UserInteractor

    @Binds
    abstract fun bindBankCardInteractorImpl_to_bankCardInteractor(bankCardsInteractorImpl: BankCardsInteractorImpl): BankCardsInteractor

    @Binds
    abstract fun bindSettingsInteractorImpl_to_settingsInteractor(settingsInteractorImpl: SettingsInteractorImpl): SettingsInteractor

}
