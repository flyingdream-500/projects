package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.args.DEFAULT_BANK_CARD
import com.example.finalproject.presentation.tools.RxImmediateSchedulerRule
import io.mockk.*
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Тестирование view model [MyCardsViewModel] отображения банковских карт
 */
class MyCardsViewModelTest {

    private lateinit var viewModelTest: MyCardsViewModel

    private val bankCardsInteractor: BankCardsInteractorImpl = mockk()

    private val cardsObserver: Observer<List<BankCard>> = mockk()
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
        viewModelTest = MyCardsViewModel(
            bankCardsInteractor
        )

        viewModelTest.observeBankCards().observeForever(cardsObserver)
        viewModelTest.observeError().observeForever(errorObserver)

        every { cardsObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
    }

    /**
     * Тестирование [MyCardsViewModel.getBankCards]
     */
    @Test
    fun getBankCardsTest() {
        every { bankCardsInteractor.getBankCards() } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
        viewModelTest.getBankCards()
        verifySequence {
            cardsObserver.onChanged(arrayListOf(DEFAULT_BANK_CARD))
        }
        verify { errorObserver wasNot Called }
    }
}