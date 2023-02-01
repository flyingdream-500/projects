package com.example.networkrequestsproject.domain

import com.example.networkrequestsproject.data.BaseUserRepository
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User

class UserInteract(private val repository: BaseUserRepository) {

    fun getList(): List<User>? {
        return repository.getUsers()
    }

    fun postPerson(person: Person): String? {
        return repository.postPerson(person)
    }
}