package com.example.contentproviderapp.domain.interactor

import android.net.Uri
import io.reactivex.rxjava3.core.Single

interface ILocalImageInteractor {
    fun getLocalImages(): Single<List<Uri>>
}