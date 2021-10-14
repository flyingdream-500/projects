package com.example.neworkrequestsproject.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neworkrequestsproject.domain.model.User

class MainViewModel : ViewModel() {

    private val errorLiveData = MutableLiveData<String>()
    private val successLiveData = MutableLiveData<List<User>>()

    fun getErrorData(): LiveData<String> = errorLiveData
    fun getUsersData(): LiveData<List<User>> = successLiveData

}