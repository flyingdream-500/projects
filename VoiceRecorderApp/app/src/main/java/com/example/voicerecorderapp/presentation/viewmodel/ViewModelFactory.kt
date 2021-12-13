package com.example.voicerecorderapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.voicerecorderapp.domain.usecase.RecordsInteractor

class ViewModelFactory(
    application: Application,
    private val recordsInteractor: RecordsInteractor
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(recordsInteractor) as T
    }
}