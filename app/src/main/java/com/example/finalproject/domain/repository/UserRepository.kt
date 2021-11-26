package com.example.finalproject.domain.repository

import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.data.repository.UserRepositoryImpl
import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Single

/**
 * Интерфейс репозитория  по работе с настройками профиля
 * Реализация в [UserRepositoryImpl], данные получаем из [UserDao]
 */
interface UserRepository {
    /**
     * Метод для получения класса с даннх профиля из БД
     */
    fun getUser(): Single<User>

    /**
     * Метод для обновления данных пользователя в БД
     * @param user - класс с данными пользователя
     */
    fun updateAndGetUser(user: User): Single<User>
}