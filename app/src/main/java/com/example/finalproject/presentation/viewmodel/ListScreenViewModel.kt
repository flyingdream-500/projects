package com.example.finalproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.utils.constants.DefaultConstants.Format.CURRENCY_DATE_FORMAT
import com.example.finalproject.utils.extensions.BaseExtensions.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

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

    fun getCurrentCurrency() {
        Log.d("MVVM", "ListScreenViewModel: ${currencyInteractor.hashCode()}")
        val disposable = currencyInteractor.getFromDb()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { currentCurrencyItem ->
                    val predicate = currentCurrencyItem.date.isToday(CURRENCY_DATE_FORMAT)
                    if (predicate) {
                        currencyLiveData.value = currentCurrencyItem.currencyList
                    } else {
                        getRemoteCurrentCurrency()
                    }
                },
                { throwable ->
                    currencyErrorLiveData.value = throwable
                    currencyErrorLiveData.value = null
                },
                {
                    getRemoteCurrentCurrency()
                }
            )

        compositeDisposable.add(disposable)

    }

    private fun getRemoteCurrentCurrency() {
        val disposable = currencyInteractor.remoteCurrentCurrency()
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
                    currencyErrorLiveData.value = null
                }
            )

        compositeDisposable.add(disposable)
    }

    private fun addingCurrentCurrencyInDb(currentCurrencyItem: CurrentCurrencyItem) {
        val disposable = currencyInteractor.addingToDb(currentCurrencyItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    currencyLiveData.value = currentCurrencyItem.currencyList
                },
                { throwable ->
                    currencyErrorLiveData.value = throwable
                    currencyErrorLiveData.value = null
                }
            )
        compositeDisposable.add(disposable)
    }



    //Сохранение баланса в shared preferences и очистка compositeDisposable
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}