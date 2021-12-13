package com.example.finalproject.presentation.receiver

/**
 * Слушатель для обновления UI после получения SMS
 */
interface SmsListener {
    fun updateVerificationCode(code: String)
}