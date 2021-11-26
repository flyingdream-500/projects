package com.example.finalproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.CurrencyInteractorImpl
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.presentation.navigation.CurrencyListScreen
import com.example.finalproject.utils.constants.DefaultConstants.Format.CURRENCY_DATE_FORMAT
import com.example.finalproject.utils.extensions.BaseExtensions.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


/**
 * View Model для экрана [CurrencyListScreen]
 * @param currencyInteractor интерактор для взаимодействия с курсами валют, реализация [CurrencyInteractorImpl]
 */
@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor,
) : ViewModel() {

    // Live Data Currency
    private val currencyLiveData = MutableLiveData<List<Currency>>()
    private val currencyProgressLiveData = MutableLiveData<Boolean>()
    private val currencyErrorLiveData = MutableLiveData<Throwable?>()

    // Observers Currency
    fun observeCurrency() = currencyLiveData as LiveData<List<Currency>>
    fun observeCurrencyProgress() = currencyProgressLiveData as LiveData<Boolean>
    fun observeCurrencyError() = currencyErrorLiveData as LiveData<Throwable?>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()


    /**
     * Запрашиваем данные по курсам валют из БД
     * Проверяем данные на актуальность
     * Если данные не сегодняшние, тянем данные с API
     */
    fun getCurrentCurrency() {
        val disposable = currencyInteractor.getCurrentCurrencyItem()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currentCurrencyItem ->
                    val currencyIsToday = currentCurrencyItem.date.isToday(CURRENCY_DATE_FORMAT)
                    if (currencyIsToday) {
                        currencyLiveData.value = currentCurrencyItem.currencyList
                    } else {
                        getRemoteCurrentCurrency()
                    }
                },
                { throwable ->
                    currencyErrorLiveData.value = throwable
                },
                {
                    getRemoteCurrentCurrency()
                }
            )

        compositeDisposable.add(disposable)

    }


    /**
     * Запрашиваем данные по курсам валют c API
     * После успешного получения данных записываем их в БД
     */
    private fun getRemoteCurrentCurrency() {
        val disposable = currencyInteractor.getRemoteCurrentCurrency()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { currencyProgressLiveData.value = true }
            .subscribe(
                { currentCurrencyItem ->
                    currencyProgressLiveData.value = false
                    addingCurrentCurrencyInDb(currentCurrencyItem)
                },
                { throwable ->
                    currencyProgressLiveData.value = false
                    currencyErrorLiveData.value = throwable
                }
            )

        compositeDisposable.add(disposable)
    }

    /**
     * Добавляем данные о курсах валют в БД
     */
    private fun addingCurrentCurrencyInDb(currentCurrencyItem: CurrentCurrencyItem) {
        val disposable = currencyInteractor.addCurrentCurrencyItem(currentCurrencyItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    currencyLiveData.value = currentCurrencyItem.currencyList
                },
                { throwable ->
                    currencyErrorLiveData.value = throwable
                }
            )
        compositeDisposable.add(disposable)
    }


    //Очистка compositeDisposable
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}