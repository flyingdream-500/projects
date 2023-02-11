package com.example.networkrequestsproject.domain

import com.example.networkrequestsproject.data.common.ApiResult
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User

interface UserRepository {
    suspend fun postPerson(person: Person): ApiResult<String?>
    suspend fun getUsers(): ApiResult<List<User>?>
}