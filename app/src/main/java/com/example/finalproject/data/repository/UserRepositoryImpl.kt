package com.example.finalproject.data.repository

import com.example.finalproject.data.db.dao.UserDao
import com.example.finalproject.domain.repository.UserRepository
import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация репозитория [UserRepository] по работе с настройками
 * Создан в [com.example.cleararchcurrency.presentation.viewmodel.ViewModelFactory]
 * Параметры:
 * @param currencyStore сохранение и получение данных о пользователе с SharedPreferences[com.example.cleararchcurrency.data.store.StoreImpl]
 */
class UserRepositoryImpl(private val userDao: UserDao): UserRepository {

    override fun getUser(): Single<User> {
        return userDao.getUser()
    }

    override fun updateUser(user: User): Completable {
        return userDao.updateUser(user)
    }

}