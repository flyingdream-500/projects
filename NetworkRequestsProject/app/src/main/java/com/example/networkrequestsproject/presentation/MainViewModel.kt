package com.example.networkrequestsproject.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkrequestsproject.data.common.ApiResult
import com.example.networkrequestsproject.domain.UserInteract
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import kotlinx.coroutines.launch
import okio.IOException

class MainViewModel(private val interact: UserInteract) : ViewModel() {

    private val errorLiveData = MutableLiveData<String>()
    private val postLiveData = MutableLiveData<String>()
    private val usersLiveData = MutableLiveData<List<User>>()

    fun getErrorData(): LiveData<String> = errorLiveData
    fun getPostData(): LiveData<String> = postLiveData
    fun getUsersData(): LiveData<List<User>> = usersLiveData

    fun getUsers() {
        viewModelScope.launch {
            try {
                when (val usersList = interact.getList()) {
                    is ApiResult.Success -> usersLiveData.value = usersList.data ?: emptyList()
                    is ApiResult.Error -> errorLiveData.value = usersList.exception.message
                }
            } catch (e: IOException) {
                errorLiveData.value = e.message
            }
        }
    }

    fun postPerson(person: Person) {
        viewModelScope.launch {
            try {
                when (val response = interact.postPerson(person)) {
                    is ApiResult.Success -> postLiveData.value = response.data ?: ""
                    is ApiResult.Error -> errorLiveData.value = response.exception.message
                }
            } catch (e: IOException) {
                errorLiveData.value = e.message
            }
        }
    }
}