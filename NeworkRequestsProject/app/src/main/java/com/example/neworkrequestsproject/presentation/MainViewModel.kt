package com.example.neworkrequestsproject.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neworkrequestsproject.domain.UserInteract
import com.example.neworkrequestsproject.domain.background.Background
import com.example.neworkrequestsproject.domain.background.RxBackground
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.domain.model.User

class MainViewModel(interact: UserInteract) : ViewModel() {

    private var background: Background = RxBackground(interact)

    private val errorLiveData = MutableLiveData<String>()
    private val postLiveData = MutableLiveData<String>()
    private val usersLiveData = MutableLiveData<List<User>>()

    fun getErrorData(): LiveData<String> = errorLiveData
    fun getPostData(): LiveData<String> = postLiveData
    fun getUsersData(): LiveData<List<User>> = usersLiveData

    fun getUsers() {
        background.getUsers(usersLiveData, errorLiveData)
    }

    fun postPerson(person: Person) {
        background.postPerson(postLiveData, errorLiveData, person)
    }

    override fun onCleared() {
        super.onCleared()
        background.close()
    }
}