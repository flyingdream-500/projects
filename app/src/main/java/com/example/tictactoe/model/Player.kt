package com.example.tictactoe.model

data class Player(val name: String)

fun Player.isMan(): Boolean = this.name == "X"