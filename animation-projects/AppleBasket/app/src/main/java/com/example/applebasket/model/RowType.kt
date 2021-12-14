package com.example.applebasket.model

import android.os.Parcelable

interface RowType : Parcelable {
    val rowType: Int
}

const val BASKET_ROW_TYPE = 1
const val APPLE_ROW_TYPE = 2
const val SUM_ROW_TYPE = 3