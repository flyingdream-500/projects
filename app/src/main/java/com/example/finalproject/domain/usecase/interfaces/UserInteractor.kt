package com.example.finalproject.domain.usecase.interfaces

import com.example.finalproject.data.repository.UserRepositoryImpl
import com.example.finalproject.domain.usecase.UserInteractorImpl
import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Single

/**
 * Интерфейс интерактора по работе с настройками профиля
 * Реализация в [UserInteractorImpl], используем [UserRepositoryImpl]
 */
interface UserInteractor {

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