package com.example.finalproject.domain.repository

import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import java.io.File

interface SettingsRepository {

     fun setAuthCheck(check: Boolean)

     fun getAuthCheck(): Boolean

     fun saveAvatarFile(uri: Uri, targetFile: File): Completable
}