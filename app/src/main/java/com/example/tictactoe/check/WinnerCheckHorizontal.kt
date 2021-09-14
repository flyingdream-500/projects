package com.example.tictactoe.check

import com.example.tictactoe.interfaces.WinnerCheck
import com.example.tictactoe.model.Game
import com.example.tictactoe.model.Player
import com.example.tictactoe.utils.Const
import com.example.tictactoe.utils.Const.COL_COUNT
import com.example.tictactoe.utils.Const.ROW_COUNT

class WinnerCheckHorizontal(private val game: Game): WinnerCheck {

    override fun checkWinner(): Player? {
        val field = game.getField()
        var currentPlayer: Player?
        var lastPlayer: Player?
        for (i in 0 until ROW_COUNT) {
            lastPlayer = null
            var successCounter = 1
            for (j in 0 until COL_COUNT) {
                currentPlayer = field[i][j].player
                if (currentPlayer == lastPlayer && (currentPlayer != null && lastPlayer != null)) {
                    successCounter++
                    if (successCounter == ROW_COUNT)
                        return currentPlayer
                }
                lastPlayer = currentPlayer
            }
        }
        return null
    }
}