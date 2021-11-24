package com.example.finalproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.user.User
import com.example.finalproject.utils.constants.DefaultConstants.Format.CURRENCY_DATE_FORMAT
import com.example.finalproject.utils.extensions.BaseExtensions.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor,
    private val bankCardsInteractor: BankCardsInteractor,
    private val userInteractor: UserInteractor
): ViewModel() {

    // Live Data Currency
    private val currencyLiveData = MutableLiveData<List<Currency>>()
    private val currencyProgressLiveData = MutableLiveData<Boolean>()
    private val currencyErrorLiveData = MutableLiveData<Throwable?>()

    // Live Data Bank Cards
    private val bankCardsLiveData = MutableLiveData<List<BankCard>>()

    //Live Data User
    private val userLiveData = MutableLiveData<User>()

    //Observer User
    fun observeUser() = userLiveData as LiveData<User>

    //Observer Bank Cards
    fun observeBankCards() = bankCardsLiveData as LiveData<List<BankCard>>

    // Observers Currency
    fun observeCurrency() = currencyLiveData as LiveData<List<Currency>>
    fun observeCurrencyProgress() = currencyProgressLiveData as LiveData<Boolean>
    fun observeCurrencyError() = currencyErrorLiveData as LiveData<Throwable?>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()



    fun getCurrentCurrency() {
        Log.d("MVVM", "MainViewModel: ${currencyInteractor.hashCode()}")
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


    fun getUser() {
        val disposable = userInteractor.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    userLiveData.value = it
                },
                {

                }
            )
        compositeDisposable.add(disposable)
    }

    fun getBankCards() {
        val disposable = bankCardsInteractor.getBankCards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val list = ArrayList(it)
                bankCardsLiveData.value = list
            },
                {

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