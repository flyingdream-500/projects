package com.example.neworkrequestsproject.domain

import com.example.neworkrequestsproject.data.BaseUserRepository
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.domain.model.User

class UserInteract(private val repository: BaseUserRepository) {

    fun getList(): List<User>? {
        return repository.getUsers()
    }

    fun postPerson(person: Person): String? {
        return repository.postPerson(person)
    }
}