package com.example.applebasket.model

data class Basket(val name: String = "default") {
    var appleList: ArrayList<Apple> = ArrayList()
}