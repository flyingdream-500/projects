package com.example.fileexplorer.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fileexplorer.data.ExternalFilesRepositoryImpl

class ViewModelFactory(
    application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = ExternalFilesRepositoryImpl()
        return SharedViewModel(repository) as T
    }
}