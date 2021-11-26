package com.example.finalproject.utils.common

import android.content.Intent

object SMSReceiver {
    /*
     * Ваш код подтверждения валютной операции: 895869.
     * Никому не сообщайте код.
     */

    private const val regex = "[0-9]+"
    //получаем код из тела сообщения
    fun getVerifyCodeFromMessage(message: String): String {
        val regex = regex.toRegex()
        val matcher = regex.find(message)
        return matcher?.value ?: ""
    }
}

object Settings {

    fun takePhotoIntent(): Intent {
        return Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
    }

}


