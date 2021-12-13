package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.domain.usecase.interfaces.CurrencyInteractor
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.args.CURRENT_CURRENCY_ITEM
import com.example.finalproject.presentation.tools.RxImmediateSchedulerRule
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Тестирование view model [ListScreenViewModel] отображения курсов валют
 */
class ListScreenViewModelTest {

    private lateinit var viewModelTest: ListScreenViewModel

    private val currencyInteractor: CurrencyInteractor = mockk()

    private val currencyObserver: Observer<List<Currency>> = mockk()
    private val progressObserver: Observer<Boolean> = mockk()
    private val errorObserver: Observer<Throwable?> = mockk()

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
        viewModelTest = ListScreenViewModel(
            currencyInteractor
        )

        viewModelTest.observeCurrency().observeForever(currencyObserver)
        viewModelTest.observeCurrencyProgress().observeForever(progressObserver)
        viewModelTest.observeCurrencyError().observeForever(errorObserver)


        every { currencyObserver.onChanged(any()) } just Runs
        every { progressObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
    }

    /**
     * Тестирование [ListScreenViewModel.getCurrentCurrency]
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

}