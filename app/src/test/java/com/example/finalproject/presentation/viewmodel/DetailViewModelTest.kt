package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.args.CURRENT_TRANSACTION
import com.example.finalproject.args.DEFAULT_BANK_CARD
import com.example.finalproject.domain.usecase.interfaces.BankCardsInteractor
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import com.example.finalproject.domain.usecase.interfaces.TransactionInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.presentation.tools.RxImmediateSchedulerRule
import com.example.finalproject.utils.common.Abbreviation.USD
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Тестирование view model [DetailScreenViewModel] экрана покупки валюты
 */
class DetailViewModelTest {

    private lateinit var viewModelTest: DetailScreenViewModel

    private val settingsInteractor: SettingsInteractor = mockk()
    private val transactionsInteractor: TransactionInteractor = mockk()
    private val bankCardInteractor: BankCardsInteractor = mockk()


    private val bankCardsObserver: Observer<List<BankCard>> = mockk()
    private val baseBankCardObserver: Observer<BankCard> = mockk()
    private val errorObserver: Observer<Throwable?> = mockk()
    private val buyingTriggerObserver: Observer<Boolean> = mockk()

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
        viewModelTest = DetailScreenViewModel(
            bankCardInteractor, settingsInteractor, transactionsInteractor
        )

        viewModelTest.observeBaseBankCard().observeForever(baseBankCardObserver)
        viewModelTest.observeBankCards().observeForever(bankCardsObserver)
        viewModelTest.observeError().observeForever(errorObserver)
        viewModelTest.observeBuyingTrigger().observeForever(buyingTriggerObserver)

        every { baseBankCardObserver.onChanged(any()) } just Runs
        every { buyingTriggerObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
        every { bankCardsObserver.onChanged(any()) } just Runs
    }

    /**
     * Тестирование [DetailScreenViewModel.getBankCards]
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


    /**
     * Тестирование [DetailScreenViewModel.addBankCards]
     */
    @Test
    fun addAndGetBankCardsTest() {
        every { bankCardInteractor.addAndGetCards(DEFAULT_BANK_CARD) } returns Single.fromCallable {
            arrayListOf(
                DEFAULT_BANK_CARD
            )
        }
        viewModelTest.addBankCards(DEFAULT_BANK_CARD)
        verifySequence {
            bankCardsObserver.onChanged(arrayListOf(DEFAULT_BANK_CARD))
        }
        verify { errorObserver wasNot Called }
    }

    /**
     * Тестирование [DetailScreenViewModel.getBankCardByAbbr]
     */
    @Test
    fun getBankCardByAttrTest() {
        every { bankCardInteractor.getBankCardByAbbr(USD.name) } returns Single.fromCallable {
            DEFAULT_BANK_CARD
        }
        viewModelTest.getBankCardByAbbr(USD.name)
        verifySequence {
            baseBankCardObserver.onChanged(DEFAULT_BANK_CARD)
        }
        verify { errorObserver wasNot Called }
    }

    /**
     * Тестирование [DetailScreenViewModel.addTransaction]
     */
    @Test
    fun addTransactionTest() {
        every { transactionsInteractor.addTransaction(CURRENT_TRANSACTION) } returns Completable.fromCallable {}
        viewModelTest.addTransaction(CURRENT_TRANSACTION)
        verify { errorObserver wasNot Called }
    }


}