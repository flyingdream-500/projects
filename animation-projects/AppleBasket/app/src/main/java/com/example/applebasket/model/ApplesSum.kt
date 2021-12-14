package com.example.applebasket.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class ApplesSum(val sum: Int) : RowType {
    override val rowType: Int
        get() = SUM_ROW_TYPE
}