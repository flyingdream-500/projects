package com.example.neworkrequestsproject.domain.background

import androidx.lifecycle.MutableLiveData
import com.example.neworkrequestsproject.domain.UserRepository
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.domain.model.User

interface Background {
    fun close()
    fun postPerson(
        postLiveData: MutableLiveData<String>,
        errorLiveData: MutableLiveData<String>,
        person: Person
    )

    fun getUsers(
        usersLiveData: MutableLiveData<List<User>>,
        errorLiveData: MutableLiveData<String>
    )
}