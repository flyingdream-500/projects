package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.domain.usecase.CurrencyInteractorImpl
import com.example.finalproject.domain.usecase.UserInteractorImpl
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.user.User
import com.example.finalproject.args.CURRENT_CURRENCY_ITEM
import com.example.finalproject.args.DEFAULT_BANK_CARD
import com.example.finalproject.args.DEFAULT_USER
import com.example.finalproject.presentation.tools.RxImmediateSchedulerRule
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Тестирование view model [MainViewModel] отображения главного экрана
 */
class MainViewModelTest {

    private lateinit var viewModelTest: MainViewModel

    private val currencyInteractor: CurrencyInteractorImpl = mockk()
    private val bankCardInteractor: BankCardsInteractorImpl = mockk()
    private val userInteractor: UserInteractorImpl = mockk()

    private val currencyObserver: Observer<List<Currency>> = mockk()
    private val progressObserver: Observer<Boolean> = mockk()
    private val errorObserver: Observer<Throwable?> = mockk()
    private val bankCardsObserver: Observer<List<BankCard>> = mockk()
    private val userObserver: Observer<User> = mockk()


    // Правило для синхронной работы RxJava
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    /** Правило тестирования JUnit, которое заменяет фонового исполнителя,
     * используемого компонентами архитектуры, другим, который выполняет каждую задачу синхронно
     */
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        viewModelTest = MainViewModel(
            currencyInteractor,
            bankCardInteractor,
            userInteractor
        )

        viewModelTest.observeCurrency().observeForever(currencyObserver)
        viewModelTest.observeCurrencyProgress().observeForever(progressObserver)
        viewModelTest.observeError().observeForever(errorObserver)
        viewModelTest.observeBankCards().observeForever(bankCardsObserver)
        viewModelTest.observeUser().observeForever(userObserver)

        every { currencyObserver.onChanged(any()) } just Runs
        every { progressObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
        every { bankCardsObserver.onChanged(any()) } just Runs
        every { userObserver.onChanged(any()) } just Runs

    }

    /**
     * Тестирование [MainViewModel.getCurrentCurrency]
     */
    @Test
    fun getCurrentCurrencyTest() {
        every { currencyInteractor.getCurrentCurrencyItem() } returns Maybe.fromCallable { CURRENT_CURRENCY_ITEM }
        every { currencyInteractor.getRemoteCurrentCurrency() } returns Single.fromCallable { CURRENT_CURRENCY_ITEM }
        every { currencyInteractor.addCurrentCurrencyItem(CURRENT_CURRENCY_ITEM) } returns Completable.fromAction { }
        viewModelTest.getCurrentCurrency()
        verifySequence {
            currencyObserver.onChanged(CURRENT_CURRENCY_ITEM.currencyList)
        }
        verify { errorObserver wasNot Called }
    }


    /**
     * Тестирование [MainViewModel.getUser]
     */
    @Test
    fun getUserTest() {
        every { userInteractor.getUser() } returns Single.fromCallable {
            DEFAULT_USER
        }
        viewModelTest.getUser()
        verifySequence {
            userObserver.onChanged(DEFAULT_USER)
        }
        verify { errorObserver wasNot Called }
    }


    /**
     * Тестирование [MainViewModel.getBankCards]
     */
    @Test
    fun getBankCardsTest() {
        every { bankCardInteractor.getBankCards() } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
        viewModelTest.getBankCards()
        verifySequence {
            bankCardsObserver.onChanged(arrayListOf(DEFAULT_BANK_CARD))
        }
        verify { errorObserver wasNot Called }
    }

}