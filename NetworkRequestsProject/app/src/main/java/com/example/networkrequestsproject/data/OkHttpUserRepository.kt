package com.example.networkrequestsproject.data

import com.example.networkrequestsproject.data.converter.UserConverter
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException


class OkHttpUserRepository(converter: UserConverter) : BaseUserRepository(converter) {

    private val httpClient = OkHttpClient.Builder().build()

    override fun postPerson(person: Person): String? {
        val request = Request.Builder()
            .url(REQUEST_URL_POST)
            .post(getRequestBody(person)!!.toRequestBody("application/json".toMediaType()))
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException(response.message)
            val body = response.body
            return body?.string()
        }
    }


    override fun getUsers(): List<User>? {
        val request = Request.Builder()
            .url(REQUEST_URL)
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException(response.message)
            val body = response.body
            return getListResponse(body?.string()!!)?.data
        }
    }
}