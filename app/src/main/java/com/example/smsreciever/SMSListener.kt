package com.example.smsreciever

/**
 * Listener for updating UI after receiving SMS
 */
interface SMSListener {
    fun updateVerificationCode(code: String)
}