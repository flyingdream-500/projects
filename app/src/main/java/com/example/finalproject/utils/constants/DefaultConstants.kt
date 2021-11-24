package com.example.finalproject.utils.constants

import com.example.finalproject.R

object DefaultConstants {

    private const val PACKAGE_NAME= "com.example.finalproject"
    const val DEFAULT_BALANCE = 1_000_000.00
    const val DEFAULT_USER_NAME = "Ivan"
    const val DEFAULT_USER_SURNAME = "Ivanov"
    const val DEFAULT_USER_AVATAR = "android.resource://$PACKAGE_NAME/${R.mipmap.default_user}"

    /**
     * Константы формата даты
     */
    object Format {
        const val CURRENCY_DATE_FORMAT = "yyyy-MM-dd"
    }

    /**
     * Константы для передачи данных по bundle
     */
    object FragmentBundles {
        const val CURRENCY_ITEM_KEY = "currency item key"
    }



}