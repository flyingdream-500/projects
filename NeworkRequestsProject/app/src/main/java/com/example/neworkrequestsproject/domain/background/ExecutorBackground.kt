package com.example.neworkrequestsproject.domain.background

import androidx.lifecycle.MutableLiveData
import com.example.neworkrequestsproject.domain.UserInteract
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.domain.model.User
import okio.IOException
import java.util.concurrent.Executors

class ExecutorBackground(private val interact: UserInteract) : Background {

    private val executorService = Executors.newSingleThreadExecutor()

    override fun close() {
        executorService.shutdownNow()
    }

    override fun postPerson(
        postLiveData: MutableLiveData<String>,
        errorLiveData: MutableLiveData<String>,
        person: Person) {
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

    override fun getUsers(
        usersLiveData: MutableLiveData<List<User>>,
        errorLiveData: MutableLiveData<String>
    ) {
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
}