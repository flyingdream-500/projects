package com.example.neworkrequestsproject.data.converter

import com.example.neworkrequestsproject.domain.model.PageOfUsers
import com.example.neworkrequestsproject.domain.model.Person
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class UserConverter {

    private var personJsonAdapter: JsonAdapter<Person>?
    private var listUserJsonAdapter: JsonAdapter<PageOfUsers>?

    init {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        personJsonAdapter = moshi.adapter(Person::class.java)
        listUserJsonAdapter = moshi.adapter(PageOfUsers::class.java)
    }

    fun convertToJson(person: Person): String? {
        return personJsonAdapter?.toJson(person)
    }

    fun convertToList(pageOfList: String): PageOfUsers? {
        return listUserJsonAdapter?.fromJson(pageOfList)
    }

}