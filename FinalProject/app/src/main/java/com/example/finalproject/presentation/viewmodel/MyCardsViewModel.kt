package com.example.finalproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.data.repository.BankCardRepositoryImpl
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.presentation.navigation.MyCardsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


/**
 * View Model для экрана [MyCardsScreen]
 *
 * @param bankCardsInteractor интерактор для взаимодействия с банковскими картами, реализация [BankCardRepositoryImpl]
 */
@HiltViewModel
class MyCardsViewModel @Inject constructor(
    private val bankCardsInteractor: BankCardsInteractor
) : ViewModel() {

    // Live Data Bank Cards
    private val bankCardsLiveData = MutableLiveData<List<BankCard>>()
    private val errorLiveData = MutableLiveData<Throwable?>()

    //Observe Bank Cards
    fun observeBankCards() = bankCardsLiveData as LiveData<List<BankCard>>
    fun observeError() = errorLiveData as LiveData<Throwable?>

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