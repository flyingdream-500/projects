package com.example.networkrequestsproject.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkrequestsproject.domain.UserInteract
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import okio.IOException
import java.util.concurrent.Executors

class MainViewModel(private val interact: UserInteract) : ViewModel() {

    private val executorService = Executors.newSingleThreadExecutor()

    private val errorLiveData = MutableLiveData<String>()
    private val postLiveData = MutableLiveData<String>()
    private val usersLiveData = MutableLiveData<List<User>>()

    fun getErrorData(): LiveData<String> = errorLiveData
    fun getPostData(): LiveData<String> = postLiveData
    fun getUsersData(): LiveData<List<User>> = usersLiveData

    fun getUsers() {
        executorService.submit {
            try {
                val usersList = interact.getList()
                usersList?.let {
                    usersLiveData.postValue(it)
                }
            } catch (e: IOException) {
                errorLiveData.postValue(e.message)
            }
        }
    }

    fun postPerson(person: Person) {
        executorService.submit {
            try {
                val response = interact.postPerson(person)
                response?.let {
                    postLiveData.postValue(it)
                }
            } catch (e: IOException) {
                errorLiveData.postValue(e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        executorService.shutdownNow()
    }
}