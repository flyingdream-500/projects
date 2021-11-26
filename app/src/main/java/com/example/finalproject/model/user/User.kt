package com.example.finalproject.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finalproject.utils.constants.DataBaseConstants.USER_TABLE_NAME
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_AVATAR

/**
 * Дата класс модели для отображения валютной операции.
 *
 * @param name       имя пользователя
 * @param surname    фамилия пользователя
 * @param avatar     uri аватара в виде строки
 */
@Entity(tableName = USER_TABLE_NAME)
data class User(
    @PrimaryKey
    val id: Int = 1,
    var name: String,
    var surname: String,
    var avatar: String = DEFAULT_USER_AVATAR,
)