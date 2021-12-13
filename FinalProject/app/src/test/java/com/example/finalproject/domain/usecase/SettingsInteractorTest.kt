package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.repository.SettingsRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Тестирование интерактора [SettingsInteractorImpl] по работе с настройками
 */
class SettingsInteractorTest {

    private val settingsRepository: SettingsRepository = mockk()

    private val settingsInteractor = SettingsInteractorImpl(settingsRepository)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { settingsRepository.getAuthCheck() } returns true
    }

    /**
     * Тестирование [SettingsInteractorImpl.getAuthCheck]
     */
    @Test
    fun getAuthTest() {
        val expectedValue = true
        val result = settingsInteractor.getAuthCheck()
        assertEquals(expectedValue, result)
    }
}