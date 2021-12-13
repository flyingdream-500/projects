package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.domain.usecase.TransactionsInteractorImpl
import com.example.finalproject.model.currency.Currency
import com.example.finalproject.model.transaction.CurrencyTransaction
import com.example.finalproject.args.CURRENT_TRANSACTION
import com.example.finalproject.presentation.tools.RxImmediateSchedulerRule
import io.mockk.*
import io.reactivex.rxjava3.core.Maybe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Тестирование view model [TransactionsViewModel] выполненных валютных операции
 */
class TransactionsViewModelTest {

    private lateinit var viewModel: TransactionsViewModel

    private val transactionsInteractor: TransactionsInteractorImpl = mockk()

    private val transactionsObserver: Observer<List<CurrencyTransaction>> = mockk()
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
        viewModel = TransactionsViewModel(
            transactionsInteractor
        )

        viewModel.observeTransactions().observeForever(transactionsObserver)
        viewModel.observeTransactionsProgress().observeForever(progressObserver)
        viewModel.observeTransactionsError().observeForever(errorObserver)

        every { transactionsObserver.onChanged(any()) } just Runs
        every { progressObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
    }

    /**
     * Тестирование [TransactionsViewModel.getTransactions]
     */
    @Test
    fun getTransactionsTest() {
        every { transactionsInteractor.getTransactions() } returns Maybe.fromCallable {
            arrayListOf(
                CURRENT_TRANSACTION
            )
        }
        viewModel.getTransactions()
        verifySequence {
            progressObserver.onChanged(true)
            progressObserver.onChanged(false)
            transactionsObserver.onChanged(arrayListOf(CURRENT_TRANSACTION))
        }
        verify { errorObserver wasNot Called }
    }

}