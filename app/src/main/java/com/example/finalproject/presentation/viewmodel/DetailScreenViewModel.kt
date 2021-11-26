package com.example.finalproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.domain.usecase.SettingsInteractorImpl
import com.example.finalproject.domain.usecase.TransactionsInteractorImpl
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import com.example.finalproject.domain.usecase.interfaces.TransactionInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.presentation.dialog.settings.AuthenticationDialog
import com.example.finalproject.presentation.navigation.CurrencyDetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


/**
 * View Model для экрана [CurrencyDetailScreen]
 *
 * @param settingsInteractor интерактор для взаимодействия с настройками приложения, реализация [SettingsInteractorImpl]
 * @param bankCardsInteractor интерактор для взаимодействия с банковскими картами, реализация [BankCardsInteractorImpl]
 * @param transactionsInteractor интерактор для взаимодействия с выполненными валютными операциями, реализация [TransactionsInteractorImpl]
 */
@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val bankCardsInteractor: BankCardsInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val transactionsInteractor: TransactionInteractor
) : ViewModel() {

    // Live Data Bank Cards
    private val bankCardsLiveData = MutableLiveData<List<BankCard>>()
    private val baseBankCardLiveData = MutableLiveData<BankCard>()
    private val errorLiveData = MutableLiveData<Throwable>()
    private val buyingTriggerLiveData = MutableLiveData<Boolean>()

    //Observe Bank Cards
    fun observeBankCards() = bankCardsLiveData as LiveData<List<BankCard>>
    fun observeBaseBankCard() = baseBankCardLiveData as LiveData<BankCard>
    fun observeError() = errorLiveData as LiveData<Throwable>
    fun observeBuyingTrigger() = buyingTriggerLiveData as LiveData<Boolean>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()

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
                }
            )
        compositeDisposable.add(disposable)
    }

    /**
     * Обновляем / добавляем банковскую карту в БД и получаем обновленные данные
     */
    fun addBankCards(vararg cards: BankCard) {
        val disposable = bankCardsInteractor.addAndGetCards(*cards)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    bankCardsLiveData.value = list
                },
                { throwable ->
                    errorLiveData.value = throwable
                }
            )

        compositeDisposable.add(disposable)
    }


    /**
     * Получаем данные о базовой банковской карте из БД
     */
    fun getBankCardByAbbr(abbreviation: String) {
        val disposable =
            bankCardsInteractor.getBankCardByAbbr(abbreviation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { bankCard ->
                        baseBankCardLiveData.value = bankCard
                    },
                    { throwable ->
                        errorLiveData.value = throwable
                    }
                )
        compositeDisposable.add(disposable)
    }

    /**
     * Получаем данные из настроек об аутентификации
     */
    fun getSettingsAuth(): Boolean {
        return settingsInteractor.getAuthCheck()
    }

    /**
     * Добавляем успешно выполненную валютную операцию в БД
     */
    fun addTransaction(currencyTransaction: CurrencyTransaction) {
        val disposable =
            transactionsInteractor.addTransaction(currencyTransaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        // success transaction
                    },
                    { throwable ->
                        errorLiveData.value = throwable
                    }
                )
        compositeDisposable.add(disposable)

    }


    /**
     * Триггеры для покупки валюты из [AuthenticationDialog]
     */
    fun triggerToBuy() {
        buyingTriggerLiveData.value = true
    }
    fun triggerToClose() {
        buyingTriggerLiveData.value = false
    }


    //Очистка compositeDisposable
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}