package com.example.finalproject.domain.usecase.interfaces

import android.content.SharedPreferences
import android.net.Uri
import com.example.finalproject.data.repository.SettingsRepositoryImpl
import com.example.finalproject.domain.usecase.SettingsInteractorImpl
import io.reactivex.rxjava3.core.Completable
import java.io.File

/**
 * Интерфейс интерактора по работе с настройками
 * Реализация в [SettingsInteractorImpl], используем [SettingsRepositoryImpl]
 */
interface SettingsInteractor {

     /**
      * Метод для добавления условия о необходимости СМС проверки при валютной операции в [SharedPreferences]
      * @param check - условие для аутентификации
      */
     fun setAuthCheck(check: Boolean)

     /**
      * Метод для получения условия о необходимости СМС проверки при валютной операции из [SharedPreferences]
      */
     fun getAuthCheck(): Boolean

     /**
      * Метод для записи файла выбранной аватарки в Internal Storage
      * @param uri - адрес выбранного аватара в памяти устройства
      * @param targetFile - файл для записи аватара
      */
     fun saveAvatarFile(uri: Uri, targetFile: File): Completable
}