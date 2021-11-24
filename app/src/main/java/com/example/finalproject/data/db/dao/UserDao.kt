package com.example.finalproject.data.db.dao

import androidx.room.*
import com.example.finalproject.model.user.User
import com.example.finalproject.utils.constants.DataBaseConstants.USER_TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun getUser(): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: User): Completable
}