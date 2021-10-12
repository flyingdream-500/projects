package com.example.applebasket.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Basket(
    val name: String,
    val apples: ArrayList<Apple> = ArrayList()
) : RowType {
    override val rowType: Int
        get() = BASKET_ROW_TYPE
}