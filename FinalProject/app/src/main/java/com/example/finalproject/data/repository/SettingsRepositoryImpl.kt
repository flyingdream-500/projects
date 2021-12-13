package com.example.finalproject.data.repository

import android.content.ContentResolver
import android.content.SharedPreferences
import android.net.Uri
import com.example.finalproject.domain.repository.SettingsRepository
import com.example.finalproject.utils.constants.PrefsConstants.USER_AUTH_DEFAULT
import com.example.finalproject.utils.constants.PrefsConstants.USER_AUTH_KEY
import io.reactivex.rxjava3.core.Completable
import java.io.File
import java.io.FileOutputStream

/**
 * Конкретная реализация репозитория [SettingsRepository] по работе с настройками
 * Параметры:
 * @param sharedPreferences для сохранения и получения данных о СМС подтверждении при выполнении валютной операции
 * @param contentResolver для открытия потока чтения с Uri
 */
class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val contentResolver: ContentResolver
) : SettingsRepository {


    /**
     * Метод для добавления условия о необходимости СМС проверки при валютной операции в [SharedPreferences]
     * @param check - условие для аутентификации
     */
    override fun setAuthCheck(check: Boolean) {
        sharedPreferences.edit()
            .putBoolean(USER_AUTH_KEY, check)
            .apply()
    }

    /**
     * Метод для получения условия о необходимости СМС проверки при валютной операции из [SharedPreferences]
     */
    override fun getAuthCheck(): Boolean {
        return sharedPreferences.getBoolean(USER_AUTH_KEY, USER_AUTH_DEFAULT)
    }


    /**
     * Метод для записи файла выбранной аватарки в Internal Storage
     * @param uri - адрес выбранного аватара в памяти устройства
     * @param targetFile - файл для записи аватара
     */
    override fun saveAvatarFile(uri: Uri, targetFile: File): Completable {
        return Completable.fromAction {
            contentResolver.openInputStream(uri).use { inputStream ->
                FileOutputStream(targetFile).use { fileOutputStream ->
                    inputStream?.let {
                        val buffer = ByteArray(4 * 1024)
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

}