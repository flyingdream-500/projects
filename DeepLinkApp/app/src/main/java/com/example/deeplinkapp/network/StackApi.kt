package com.example.deeplinkapp.network

import com.example.deeplinkapp.model.StackQuestions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StackApi {
    @GET("2.3/questions/{id}?site=stackoverflow")
    fun loadQuestion(@Path("id") id: String): Call<StackQuestions>
}