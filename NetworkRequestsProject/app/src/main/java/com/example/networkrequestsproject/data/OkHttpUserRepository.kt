package com.example.networkrequestsproject.data

import com.example.networkrequestsproject.data.common.ApiResult
import com.example.networkrequestsproject.data.converter.UserConverter
import com.example.networkrequestsproject.data.exception.ApiException.*
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class OkHttpUserRepository(converter: UserConverter) : BaseUserRepository(converter) {

    private val httpClient = OkHttpClient.Builder().build()

    override suspend fun postPerson(person: Person): ApiResult<String?> {
        val requestBody = getRequestBody(person)
            ?: return ApiResult.Error(RequestBodyException())
        val request = Request.Builder()
            .url(REQUEST_URL_POST)
            .post(
                requestBody.toRequestBody("application/json".toMediaType())
            )
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
                return ApiResult.Error(ResponseStatusException())
            val body = response.body
                ?: return ApiResult.Error(ResponseBodyException())
            return ApiResult.Success(body.string())
        }
    }


    override suspend fun getUsers(): ApiResult<List<User>?> {
        val request = Request.Builder()
            .url(REQUEST_URL)
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful)
                return ApiResult.Error(ResponseStatusException())
            val body = response.body
                ?: return ApiResult.Error(ResponseBodyException())
            val data = getListResponse(body.string())
                ?: return ApiResult.Error(ParsingException())
            return ApiResult.Success(data.data)
        }
    }
}