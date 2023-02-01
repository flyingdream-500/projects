package com.example.networkrequestsproject.domain

import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.domain.model.User

interface UserRepository {
     fun postPerson(person: Person): String?
     fun getUsers(): List<User>?
}