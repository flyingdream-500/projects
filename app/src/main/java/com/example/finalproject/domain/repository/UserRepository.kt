package com.example.finalproject.domain.repository

import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Методы по работе с настройками профиля
 * Реализация в [com.example.cleararchcurrency.data.repository.UserRepositoryImpl]
 */
interface UserRepository {

    fun getUser(): Single<User>

    fun updateUser(user: User): Completable
}