package com.example.finalproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import com.example.finalproject.domain.usecase.interfaces.TransactionInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.transaction.CurrencyTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val bankCardsInteractor: BankCardsInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val transactionsInteractor: TransactionInteractor
) : ViewModel() {

    private val bankCardsLiveData = MutableLiveData<List<BankCard>>()
    private val baseBankCardLiveData = MutableLiveData<BankCard>()
    private val transactionsIsAddedLiveData = MutableLiveData<Boolean>()

    fun observeBankCards() = bankCardsLiveData as LiveData<List<BankCard>>
    fun observeBankCard() = baseBankCardLiveData as LiveData<BankCard>
    fun observeTransactionsAdding() = transactionsIsAddedLiveData as LiveData<Boolean>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()

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

    fun addBankCard(bankCard: BankCard) {
        val disposable = bankCardsInteractor.addBankCard(bankCard)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getBankCards()
                Log.d("MVVM", "Success adding")
            }
        compositeDisposable.add(disposable)
    }

    fun addBankCards(vararg cards: BankCard) {
        val disposable = bankCardsInteractor.addBankCards(*cards)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getBankCards()
                Log.d("MVVM", "Success adding")
            }
        compositeDisposable.add(disposable)
    }

    fun getBankCardByAbbr(abbreviation: String) {
        val disposable =
            bankCardsInteractor.getBankCardByAbbr(abbreviation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        baseBankCardLiveData.value = it
                    },
                    {

                    }
                )
        compositeDisposable.add(disposable)
    }

    fun getSettingsAuth(): Boolean {
        return settingsInteractor.getAuthCheck()
    }

    // Добавление выполненной операции в БД
    fun addTransaction(currencyTransaction: CurrencyTransaction) {

        val disposable =
            transactionsInteractor.addTransaction(currencyTransaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d("INFO", "add success")
                        transactionsIsAddedLiveData.value = true
                    },
                    {
                        Log.d("INFO", "add throwable")
                        transactionsIsAddedLiveData.value = false
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