package com.example.finalproject.domain.usecase

import android.net.Uri
import com.example.finalproject.domain.repository.SettingsRepository
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import io.reactivex.rxjava3.core.Completable
import java.io.File

class SettingsInteractorImpl(
    private val settingsRepository: SettingsRepository
) : SettingsInteractor {
    override fun setAuthCheck(check: Boolean) {
        settingsRepository.setAuthCheck(check)
    }

    override fun getAuthCheck(): Boolean {
        return settingsRepository.getAuthCheck()
    }

    override fun saveAvatarFile(uri: Uri, targetFile: File): Completable {
        return settingsRepository.saveAvatarFile(uri, targetFile)
    }

}