package com.example.networkrequestsproject.domain

import com.example.networkrequestsproject.data.BaseUserRepository
import com.example.networkrequestsproject.data.common.ApiResult
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserInteract(private val repository: BaseUserRepository) {

    suspend fun getList(): ApiResult<List<User>?> {
        return withContext(Dispatchers.IO) {
            repository.getUsers()
        }
    }

    suspend fun postPerson(person: Person): ApiResult<String?> {
        return withContext(Dispatchers.IO) {
            repository.postPerson(person)
        }
    }
}