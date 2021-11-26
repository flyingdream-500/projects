package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.domain.usecase.BankCardsInteractorImpl
import com.example.finalproject.domain.usecase.CurrencyInteractorImpl
import com.example.finalproject.domain.usecase.UserInteractorImpl
import com.example.finalproject.model.network.Rates
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private val currencyInteractorImpl: CurrencyInteractorImpl = mockk()
    private val bankCardInteractorImpl: BankCardsInteractorImpl = mockk()
    private val userInteractorImpl: UserInteractorImpl = mockk()

    private val ratesObserver: Observer<Rates> = mockk()
    private val progressObserver: Observer<Boolean> = mockk()
    private val errorObserver: Observer<Throwable> = mockk()


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

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun observeUser() {
    }

    @Test
    fun observeBankCards() {
    }

    @Test
    fun observeCurrency() {
    }

    @Test
    fun observeCurrencyProgress() {
    }

    @Test
    fun observeError() {
    }

    @Test
    fun getCurrentCurrency() {
    }

    @Test
    fun getUser() {
    }

    @Test
    fun getBankCards() {
    }

    @Test
    fun onCleared() {
    }
}