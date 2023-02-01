package com.example.networkrequestsproject.data

import com.example.networkrequestsproject.data.converter.UserConverter
import com.example.networkrequestsproject.domain.UserRepository
import com.example.networkrequestsproject.domain.model.PageOfUsers
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User

abstract class BaseUserRepository(private val converter: UserConverter) : UserRepository {

    val REQUEST_URL = "https://reqres.in/api/users?page=1"
    val REQUEST_URL_POST = "https://reqres.in/api/users"


    fun getRequestBody(person: Person): String? {
        return converter.convertToJson(person)
    }

    fun getListResponse(source: String): PageOfUsers? {
        return converter.convertToList(source)
    }

    abstract override fun postPerson(person: Person): String?
    abstract override fun getUsers(): List<User>?
}