package com.example.networkrequestsproject.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageOfUsers(
    val data: List<User>
)

@JsonClass(generateAdapter = true)
data class Person(
    val name: String,
    val job: String
)

@JsonClass(generateAdapter = true)
data class User(
    val id: Long,
    val email: String,
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "last_name")
    val lastName: String?,
    val avatar: String?
)

