package com.example.finalproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.TransactionsInteractorImpl
import com.example.finalproject.domain.usecase.interfaces.TransactionInteractor
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.presentation.navigation.TransactionsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * View Model для экрана [TransactionsScreen]
 *
 * @param transactionsInteractor интерактор для взаимодействия с выполненными валютными операциями, реализация [TransactionsInteractorImpl]
 */
@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionsInteractor: TransactionInteractor
) : ViewModel() {

    // Live Data Transactions
    private val transactionsLiveData = MutableLiveData<List<CurrencyTransaction>>()
    private val transactionsProgressLiveData = MutableLiveData<Boolean>()
    private val transactionsErrorLiveData = MutableLiveData<Throwable?>()

    // Observers Transactions
    fun observeTransactions() = transactionsLiveData as LiveData<List<CurrencyTransaction>>
    fun observeTransactionsProgress() = transactionsProgressLiveData as LiveData<Boolean>
    fun observeTransactionsError() = transactionsErrorLiveData as LiveData<Throwable?>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()


    /**
     * Получение данных о транзакциях из БД
     */
    fun getTransactions() {

        val disposable =
            transactionsInteractor.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { transactionsProgressLiveData.value = true }
                .subscribe(
                    { transactions ->
                        transactionsProgressLiveData.value = false
                        transactionsLiveData.value = transactions
                    }, { throwable ->
                        transactionsProgressLiveData.value = false
                        transactionsErrorLiveData.value = throwable
                    }, {
                        transactionsProgressLiveData.value = false
                        transactionsLiveData.value = emptyList()
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