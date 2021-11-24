package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.repository.CurrencyRepository
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/**
 * UseCases для работы с курсом валют и обращение к соответсвущим методам
 * Параметры:
 * @see currencyRepository реализован в классе [com.example.cleararchcurrency.data.repository.CurrencyRepositoryImpl]
 * Экземпляр класса передается в [com.example.cleararchcurrency.presentation.viewmodel.SharedViewModel]
 */
class CurrencyInteractorImpl(
    private val currencyRepository: CurrencyRepository,
    private val itemConverter: CurrentCurrencyItemConverter
    ) : CurrencyInteractor {

    override fun getFromDb(): Maybe<CurrentCurrencyItem> {
        return currencyRepository.getFromDb()
    }

    override fun addingToDb(currentCurrencyItem: CurrentCurrencyItem): Completable {
        return currencyRepository.addingToDb(currentCurrencyItem)
    }

    override fun remoteCurrentCurrency(): Single<CurrentCurrencyItem> {
        return currencyRepository.getRemoteCurrentCurrency().map(itemConverter::convertToCurrentCurrencyItem)
    }
}