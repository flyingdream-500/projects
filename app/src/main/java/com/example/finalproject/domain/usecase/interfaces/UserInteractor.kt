package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserInteractor {

    fun getUser(): Single<User>

    fun updateUser(user: User): Completable
}