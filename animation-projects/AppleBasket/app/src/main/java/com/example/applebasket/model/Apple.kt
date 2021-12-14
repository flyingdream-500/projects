package com.example.applebasket.model

import kotlinx.parcelize.Parcelize

@Parcelize
data class Apple(
    val name: String,
    var owner: Basket,
) : RowType {
    override val rowType: Int
        get() = APPLE_ROW_TYPE
}