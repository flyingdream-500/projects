package com.example.finalproject.data.repository

import android.content.ContentResolver
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Тестирование репозитория [CurrencyRepositoryImpl] по работе с настройками
 */
class SettingsRepositoryTest {

    private val sharedPreferences: SharedPreferences = mockk()
    private val contentResolver: ContentResolver = mockk()

    private val settingsInteractor = SettingsRepositoryImpl(sharedPreferences, contentResolver)

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    fun setUp() {
        every { settingsInteractor.getAuthCheck() } returns true
    }

    /**
     * Тестирование [SettingsRepositoryImpl.getAuthCheck]
     */
    @Test
    fun getAuthTest() {
        val expectedValue = true
        val result = settingsInteractor.getAuthCheck()
        assertEquals(expectedValue, result)
    }

}