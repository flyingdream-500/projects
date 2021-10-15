package com.example.neworkrequestsproject.domain

import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.domain.model.User

interface UserRepository {
     fun postPerson(person: Person): String?
     fun getUsers(): List<User>?
}