package com.example.finalproject.domain.usecase

import com.example.finalproject.data.repository.UserRepositoryImpl
import com.example.finalproject.domain.repository.UserRepository
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Single


/**
 * Конкретная реализация интерфейса [UserInteractor]  для работы с настройками пользователя
 * Параметры:
 * @param userRepository репозиторий по работе с настройками пользователя, реализован в классе [UserRepositoryImpl]
 */
class UserInteractorImpl(private val userRepository: UserRepository) : UserInteractor {

    /**
     * Метод для получения класса с даннх профиля из БД
     */
    override fun getUser(): Single<User> {
        return userRepository.getUser()
    }

    /**
     * Метод для обновления данных пользователя в БД
     * @param user - класс с данными пользователя
     */
    override fun updateAndGetUser(user: User): Single<User> {
        return userRepository.updateAndGetUser(user)
    }


}