package com.example.finalproject.domain.usecase

import android.content.SharedPreferences
import android.net.Uri
import com.example.finalproject.data.repository.SettingsRepositoryImpl
import com.example.finalproject.domain.repository.SettingsRepository
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import io.reactivex.rxjava3.core.Completable
import java.io.File

/**
 * Конкретная реализация интерфейса [SettingsInteractor]  для работы с настройками
 * Параметры:
 * @param settingsRepository  репозиторий по работе с настройками, реализован в классе [SettingsRepositoryImpl]
 */
class SettingsInteractorImpl(
    private val settingsRepository: SettingsRepository
) : SettingsInteractor {

    /**
     * Метод для добавления условия о необходимости СМС проверки при валютной операции в [SharedPreferences]
     * @param check - условие для аутентификации
     */
    override fun setAuthCheck(check: Boolean) {
        settingsRepository.setAuthCheck(check)
    }

    /**
     * Метод для получения условия о необходимости СМС проверки при валютной операции из [SharedPreferences]
     */
    override fun getAuthCheck(): Boolean {
        return settingsRepository.getAuthCheck()
    }

    /**
     * Метод для записи файла выбранной аватарки в Internal Storage
     * @param uri - адрес выбранного аватара в памяти устройства
     * @param targetFile - файл для записи аватара
     */
    override fun saveAvatarFile(uri: Uri, targetFile: File): Completable {
        return settingsRepository.saveAvatarFile(uri, targetFile)
    }

}