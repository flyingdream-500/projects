package com.example.finalproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.domain.usecase.CurrencyInteractorImpl
import com.example.finalproject.domain.usecase.UserInteractorImpl
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.currency.CurrentCurrencyItem
import com.example.finalproject.model.user.User
import com.example.finalproject.presentation.navigation.MainScreen
import com.example.finalproject.utils.constants.DefaultConstants.Format.CURRENCY_DATE_FORMAT
import com.example.finalproject.utils.extensions.BaseExtensions.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * View Model для экрана [MainScreen]
 *
 * @param currencyInteractor интерактор для взаимодействия с курсами валют, реализация [CurrencyInteractorImpl]
 * @param bankCardsInteractor интерактор для взаимодействия с банковскими картами, реализация [BankCardsInteractorImpl]
 * @param userInteractor интерактор для взаимодействия с данными пользователя, реализация [UserInteractorImpl]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyInteractor: CurrencyInteractor,
    private val bankCardsInteractor: BankCardsInteractor,
    private val userInteractor: UserInteractor
) : ViewModel() {

    // Live Data Currency
    private val currencyLiveData = MutableLiveData<List<Currency>>()
    private val currencyProgressLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Throwable?>()

    // Live Data Bank Cards
    private val bankCardsLiveData = MutableLiveData<List<BankCard>>()

    //Live Data User
    private val userLiveData = MutableLiveData<User>()

    //Observe User
    fun observeUser() = userLiveData as LiveData<User>

    //Observe Bank Cards
    fun observeBankCards() = bankCardsLiveData as LiveData<List<BankCard>>

    // Observe Currency
    fun observeCurrency() = currencyLiveData as LiveData<List<Currency>>
    fun observeCurrencyProgress() = currencyProgressLiveData as LiveData<Boolean>
    fun observeError() = errorLiveData as LiveData<Throwable?>


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
                    errorLiveData.value = throwable
                    errorLiveData.value = null
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
                    errorLiveData.value = throwable
                    errorLiveData.value = null
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
                    errorLiveData.value = throwable
                    errorLiveData.value = null
                }
            )
        compositeDisposable.add(disposable)
    }


    /**
     * Получаем данные о пользователе из БД
     */
    fun getUser() {
        val disposable = userInteractor.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user ->
                    userLiveData.value = user
                },
                { throwable ->
                    errorLiveData.value = throwable
                    errorLiveData.value = null
                }
            )
        compositeDisposable.add(disposable)
    }


    /**
     * Получаем данные о банковских картах из БД
     */
    fun getBankCards() {
        val disposable = bankCardsInteractor.getBankCards()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    bankCardsLiveData.value = list
                },
                { throwable ->
                    errorLiveData.value = throwable
                    errorLiveData.value = null
                }
            )
        compositeDisposable.add(disposable)
    }

    // Очистка compositeDisposable
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}