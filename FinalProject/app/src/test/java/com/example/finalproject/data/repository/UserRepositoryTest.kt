package com.example.finalproject.data.repository

import com.example.finalproject.args.DEFAULT_USER
import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.domain.repository.UserRepository
import com.example.finalproject.domain.usecase.UserInteractorImpl
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Тестирование репозитория [UserRepositoryImpl] по работе с настройками
 */
class UserRepositoryTest {

    private val userDao: UserDao = mockk()

    private val userRepository = UserRepositoryImpl(userDao)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { userDao.getUser() } returns Single.fromCallable {
            DEFAULT_USER
        }
        every { userDao.updateAndGetUser(DEFAULT_USER) } returns Single.fromCallable {
            DEFAULT_USER
        }
    }

    /**
     * Тестирование [UserRepositoryImpl.getUser]
     */
    @Test
    fun getUserTest() {
        val expectedValue = DEFAULT_USER
        val result = userRepository.getUser().blockingGet()
        assertEquals(expectedValue, result)
    }

    /**
     * Тестирование [UserRepositoryImpl.updateAndGetUser]
     */
    @Test
    fun updateAndGetUserTest() {
        val expectedValue = DEFAULT_USER
        val result = userRepository.updateAndGetUser(DEFAULT_USER).blockingGet()
        assertEquals(expectedValue, result)
    }

}