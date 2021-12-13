package com.example.contentproviderapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contentproviderapp.domain.interactor.IDictionaryInteractor
import com.example.contentproviderapp.domain.interactor.ILocalImageInteractor


class ViewModelFactory(
    application: Application,
    private val dictionaryInteractor: IDictionaryInteractor,
    private val localImageInteractor: ILocalImageInteractor
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(dictionaryInteractor, localImageInteractor) as T
    }
}