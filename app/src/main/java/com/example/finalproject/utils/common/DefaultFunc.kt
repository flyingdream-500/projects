package com.example.finalproject.utils.common

import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

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

    fun ContextWrapper.saveAvatarToExternalFilesDir(uri: Uri, targetFile: File) {
        contentResolver.openInputStream(uri).use { inputStream ->
            FileOutputStream(targetFile).use { fileOutputStream ->
                inputStream?.let {
                    val buffer = ByteArray(4 * 1024) // buffer size
                    while (true) {
                        val byteCount = inputStream.read(buffer)
                        if (byteCount < 0) break
                        fileOutputStream.write(buffer, 0, byteCount)
                    }
                }
            }
        }
    }
}


