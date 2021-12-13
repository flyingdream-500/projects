package com.example.finalproject.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.bankcard.BankCard
import com.example.finalproject.model.user.User
import com.example.finalproject.args.DEFAULT_USER
import com.example.finalproject.presentation.tools.RxImmediateSchedulerRule
import io.mockk.*
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Тестирование view model [SettingsViewModel] отображения настроек
 */
class SettingsViewModelTest {

    private lateinit var viewModelTest: SettingsViewModel

    private val userInteractor: UserInteractor = mockk()
    private val settingsInteractor: SettingsInteractor = mockk()

    private val userObserver: Observer<User> = mockk()
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
        viewModelTest = SettingsViewModel(
            userInteractor, settingsInteractor
        )

        viewModelTest.observeUser().observeForever(userObserver)
        viewModelTest.observeUserError().observeForever(errorObserver)

        every { userObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
    }

    /**
     * Тестирование [SettingsViewModel.getUser]
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
     * Тестирование [SettingsViewModel.saveUser]
     */
    @Test
    fun saveUserTest() {
        every { userInteractor.updateAndGetUser(DEFAULT_USER)} returns Single.fromCallable {
            DEFAULT_USER
        }
        viewModelTest.saveUser(DEFAULT_USER)
        verifySequence {
            userObserver.onChanged(DEFAULT_USER)
        }
        verify { errorObserver wasNot Called }
    }


}