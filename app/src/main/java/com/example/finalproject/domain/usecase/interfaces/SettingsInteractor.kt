package com.example.finalproject.domain.usecase.interfaces

import android.content.ContentResolver
import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import java.io.File

interface SettingsInteractor {

     fun setAuthCheck(check: Boolean)

     fun getAuthCheck(): Boolean

     fun saveAvatarFile(uri: Uri, targetFile: File): Completable
}