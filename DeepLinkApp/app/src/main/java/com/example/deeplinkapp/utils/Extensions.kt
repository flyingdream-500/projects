package com.example.deeplinkapp.utils

fun String.idFromPath() = substringAfter("/questions/").substringBefore("/")