package com.example.neworkrequestsproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neworkrequestsproject.domain.UserInteract

class ViewModelFactory(private val interact: UserInteract) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(interact) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}