package com.example.finalproject.utils.common

import android.content.ContentValues
import com.example.finalproject.R
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_BALANCE
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_AVATAR
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_NAME
import com.example.finalproject.utils.constants.DefaultConstants.DEFAULT_USER_SURNAME


object DefaultClasses {

    /**
     * Функции для создания дефолтной банковской карты при первом запуске приложения
     */
    fun defaultBankCard() =
        ContentValues().apply {
            put("balance", DEFAULT_BALANCE)
            put("logo", R.drawable.ic_us)
            put("currency", R.string.us_character)
            put("color", R.color.black)
            put("abbreviation", Abbreviation.USD.name)
            put("description", R.string.us_description)
        }


    /**
     * Функции для создания данных пользователя при первом запуске приложения
     */
    fun defaultUser() =
        ContentValues().apply {
            put("name", DEFAULT_USER_NAME)
            put("surname", DEFAULT_USER_SURNAME)
            put("avatar", DEFAULT_USER_AVATAR)
        }

}