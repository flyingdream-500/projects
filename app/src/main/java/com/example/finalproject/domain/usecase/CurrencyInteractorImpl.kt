package com.example.finalproject.domain.usecase

import com.example.finalproject.data.network.api.OkHttpCurrencyApiImpl
import com.example.finalproject.data.repository.CurrencyRepositoryImpl
import com.example.finalproject.domain.repository.CurrencyRepository
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverter
import com.example.finalproject.domain.converter.CurrentCurrencyItemConverterImpl
import com.example.finalproject.model.network.CurrentCurrency
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

/**
 * Конкретная реализация интерфейса [CurrencyInteractor] для работы с курсами валют
 * Параметры:
 * @param currencyRepository репозиторий по работе с курсами валют, реализован в классе [CurrencyRepositoryImpl]
 * @param itemConverter конвертер для преобразования класса [CurrentCurrency] в [CurrentCurrencyItem],  реализован в классе [CurrentCurrencyItemConverterImpl]
 */
class CurrencyInteractorImpl(
    private val currencyRepository: CurrencyRepository,
    private val itemConverter: CurrentCurrencyItemConverter
    ) : CurrencyInteractor {

    /**
     * Метод для получения класса с курсами валют из БД
     */
    override fun getCurrentCurrencyItem(): Maybe<CurrentCurrencyItem> {
        return currencyRepository.getCurrentCurrencyItem()
    }

    /**
     * Метод для добавления класса с курсами валют в БД
     * @param currentCurrencyItem - класс с курсами валют
     */
    override fun addCurrentCurrencyItem(currentCurrencyItem: CurrentCurrencyItem): Completable {
        return currencyRepository.addCurrentCurrencyItem(currentCurrencyItem)
    }

    /**
     * Метод для получения данных через интернет из API, реализация в [OkHttpCurrencyApiImpl] и последующая конвертация в [CurrentCurrencyItem]
     */
    override fun getRemoteCurrentCurrency(): Single<CurrentCurrencyItem> {
        return currencyRepository.getRemoteCurrentCurrency().map(itemConverter::convertToCurrentCurrencyItem)
    }
}