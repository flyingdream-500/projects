package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.repository.UserRepository
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.user.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


/**
 * UseCases для работы с настройками профиля
 * Параметры:
 * @param userRepository реализован в классе [com.example.cleararchcurrency.data.repository.UserRepositoryImpl]
 * Экземпляр класса передается в [com.example.cleararchcurrency.presentation.viewmodel.SharedViewModel]
 */
class UserInteractorImpl(private val userRepository: UserRepository) : UserInteractor {
    override fun getUser(): Single<User> {
        return userRepository.getUser()
    }

    override fun updateUser(user: User): Completable {
        return userRepository.updateUser(user)
    }


}