package com.example.deeplinkapp.model

import com.google.gson.annotations.SerializedName

data class StackQuestions(
    @SerializedName("items")
    val questions: List<Question>
)