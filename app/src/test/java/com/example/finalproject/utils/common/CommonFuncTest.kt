package com.example.finalproject.utils.common

import com.example.finalproject.utils.common.SMSReceiver.getVerifyCodeFromMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class CommonFuncTest {

    /**
     * Тестирование [SMSReceiver.getVerifyCodeFromMessage]
     */
    @Test
    fun getVerifyCodeTest() {
        val message = "Ваш код подтверждения валютной операции: 895869. Никому не сообщайте код."
        val expectedValue = "895869"
        val result = getVerifyCodeFromMessage(message)
        assertEquals(expectedValue, result)
    }
}