package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.domain.repository.UserRepository
import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация репозитория [UserRepository] по работе с настройками
 * Параметры:
 * @param userDao сохранение и получение данных о пользователе с БД [UserDao]
 */
class UserRepositoryImpl(private val userDao: UserDao): UserRepository {

    /**
     * Метод для получения класса с даннх профиля из БД
     */
    override fun getUser(): Single<User> {
        return userDao.getUser()
    }

    /**
     * Метод для обновления данных пользователя в БД
     * @param user - класс с данными пользователя
     */
    override fun updateAndGetUser(user: User): Single<User> {
        return userDao.updateAndGetUser(user)
    }

}