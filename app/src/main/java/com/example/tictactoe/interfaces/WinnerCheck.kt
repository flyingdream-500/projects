package com.example.tictactoe.interfaces

import com.example.tictactoe.model.Player

interface WinnerCheck {
    fun checkWinner(): Player?
}