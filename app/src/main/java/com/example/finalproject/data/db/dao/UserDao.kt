package com.example.finalproject.data.db.dao

import androidx.room.*
import com.example.finalproject.model.user.User
import com.example.finalproject.utils.constants.DataBaseConstants.USER_TABLE_NAME
import io.reactivex.rxjava3.core.Single

/**
 * Dao с методами взаимодействия с БД пользователя
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun getUser(): Single<User>

    fun updateAndGetUser(user: User): Single<User> {
        return Single.fromCallable {
            updateAndGetUserSync(user)
        }
    }

    @Insert
    fun insertUser(user: User)

    @Transaction
    fun updateAndGetUserSync(user: User): User {
        updateUserSync(user)
        return getUserSync()
    }
    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun getUserSync(): User
    @Update
    fun updateUserSync(user: User)
}