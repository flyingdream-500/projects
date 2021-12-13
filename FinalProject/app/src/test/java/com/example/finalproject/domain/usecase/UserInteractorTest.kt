package com.example.finalproject.domain.usecase

import com.example.finalproject.args.DEFAULT_USER
import com.example.finalproject.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Тестирование интерактора [UserInteractorImpl] по работе с данными пользователя
 */
class UserInteractorTest {

    private val userRepository: UserRepository = mockk()

    private val userInteractor = UserInteractorImpl(userRepository)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { userRepository.getUser() } returns Single.fromCallable {
            DEFAULT_USER
        }
        every { userRepository.updateAndGetUser(DEFAULT_USER) } returns Single.fromCallable {
            DEFAULT_USER
        }
    }

    /**
     * Тестирование [UserInteractorImpl.getUser]
     */
    @Test
    fun getUserTest() {
        val expectedValue = DEFAULT_USER
        val result = userInteractor.getUser().blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [UserInteractorImpl.updateAndGetUser]
     */
    @Test
    fun updateAndGetUserTest() {
        val expectedValue = DEFAULT_USER
        val result = userInteractor.updateAndGetUser(DEFAULT_USER).blockingGet()
        assertEquals(expectedValue, result)
    }

}