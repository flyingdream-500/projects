package com.example.tictactoe.check

import com.example.tictactoe.interfaces.WinnerCheck
import com.example.tictactoe.model.Game
import com.example.tictactoe.model.Player
import com.example.tictactoe.utils.Const
import com.example.tictactoe.utils.Const.ROW_COUNT

class WinnerCheckDiagonalLeft(private val game: Game): WinnerCheck {

    override fun checkWinner(): Player? {
        val field = game.getField()
        var currenPlayer: Player?
        var lastPlayer: Player? = null
        var successCount = 1
        for (i in 0 until ROW_COUNT) {
            currenPlayer = field[i][i].player
            if (currenPlayer != null) {
                if (lastPlayer == currenPlayer) {
                    successCount++
                    if (successCount == ROW_COUNT)
                        return currenPlayer
                }
            }
            lastPlayer = currenPlayer
        }
        return null
    }
}