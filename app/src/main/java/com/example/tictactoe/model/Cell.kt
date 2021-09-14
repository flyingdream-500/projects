package com.example.tictactoe.model

class Cell {
    var player: Player? = null

    fun isFilled(): Boolean = player != null
}