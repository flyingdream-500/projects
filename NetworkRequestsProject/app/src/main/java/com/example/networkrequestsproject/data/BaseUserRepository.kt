package com.example.networkrequestsproject.data

import com.example.networkrequestsproject.data.common.ApiResult
import com.example.networkrequestsproject.data.converter.UserConverter
import com.example.networkrequestsproject.domain.UserRepository
import com.example.networkrequestsproject.domain.model.PageOfUsers
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import com.squareup.moshi.JsonDataException

abstract class BaseUserRepository(private val converter: UserConverter) : UserRepository {

    val REQUEST_URL = "https://reqres.in/api/users?page=1"
    val REQUEST_URL_POST = "https://reqres.in/api/users"


    fun getRequestBody(person: Person): String? {
        return converter.convertToJson(person)
    }

    @Throws(JsonDataException::class)
    fun getListResponse(source: String): PageOfUsers? {
        return converter.convertToList(source)
    }

    abstract override suspend fun postPerson(person: Person): ApiResult<String?>
    abstract override suspend fun getUsers(): ApiResult<List<User>?>
}