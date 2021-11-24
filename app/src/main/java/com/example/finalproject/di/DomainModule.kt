package com.example.finalproject.di

import android.content.ContentResolver
import android.content.SharedPreferences
import com.example.finalproject.data.db.dao.BankCardsDao
import com.example.finalproject.data.db.dao.CurrentCurrencyDao
import com.example.finalproject.data.db.dao.TransactionsDao
import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.data.network.api.CurrencyApi
import com.example.finalproject.data.repository.*
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverter
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverterImpl
import com.example.finalproject.domain.repository.*
import com.example.finalproject.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Модуль domain слоя
 */

@Module()
@InstallIn(SingletonComponent::class)
class ConverterModule {


    @Provides
    fun provideConverter() =
        CurrentCurrencyItemConverterImpl()

}

@Module()
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideCurrencyRepositoryImpl(
        currencyApi: CurrencyApi,
        currentCurrencyDao: CurrentCurrencyDao
    ) =
        CurrencyRepositoryImpl(currencyApi, currentCurrencyDao)

    @Provides
    fun provideUserRepositoryImpl(userDao: UserDao) =
        UserRepositoryImpl(userDao)

    @Provides
    fun provideTransactionsRepository(transactionsDao: TransactionsDao) =
        TransactionsRepositoryImpl(transactionsDao)

    @Provides
    fun provideBankCardsRepository(bankCardsDao: BankCardsDao) =
        BankCardRepositoryImpl(bankCardsDao)

    @Provides
    fun provideSettingsRepository(sharedPreferences: SharedPreferences, contentResolver: ContentResolver) =
        SettingsRepositoryImpl(sharedPreferences, contentResolver)

}


@Module()
@InstallIn(SingletonComponent::class)
class InteractorModule {


    //@Reusable
    //@Singleton
    @Provides
    fun provideCurrencyInteractor(
        currencyRepository: CurrencyRepository,
        converter: CurrentCurrencyItemConverter
    ) =
        CurrencyInteractorImpl(currencyRepository, converter)

    @Provides
    fun provideUserInteractor(userRepository: UserRepository) =
        UserInteractorImpl(userRepository)

    @Provides
    fun provideTransactionsInteractor(transactionsRepository: TransactionsRepository) =
        TransactionsInteractorImpl(transactionsRepository)

    @Provides
    fun provideBankCardsInteractor(bankCardsRepository: BankCardRepository) =
        BankCardsInteractorImpl(bankCardsRepository)

    @Provides
    fun provideSettingsInteractor(settingsRepository: SettingsRepository) =
        SettingsInteractorImpl(settingsRepository)
}
