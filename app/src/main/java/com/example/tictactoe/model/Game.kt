package com.example.tictactoe.model

import com.example.tictactoe.check.WinnerCheckDiagonalLeft
import com.example.tictactoe.check.WinnerCheckDiagonalRight
import com.example.tictactoe.check.WinnerCheckHorizontal
import com.example.tictactoe.check.WinnerCheckVertical
import com.example.tictactoe.interfaces.WinnerCheck
import com.example.tictactoe.utils.Const.COL_COUNT
import com.example.tictactoe.utils.Const.ROW_COUNT

class Game {

    private lateinit var players: Array<Player>

    private var field: Array<Array<Cell>>

    private var started = false

    private var currentPlayer: Player? = null

    var cellsFilled = 0

    private var cellsCount = ROW_COUNT * COL_COUNT

    private var winnerChecks: Array<WinnerCheck>

    init {
        field = Array(ROW_COUNT) { row ->
            Array(COL_COUNT) { col ->
                Cell()
            }
        }
        winnerChecks = arrayOf(
            WinnerCheckHorizontal(this),
            WinnerCheckVertical(this),
            WinnerCheckDiagonalLeft(this),
            WinnerCheckDiagonalRight(this)
        )
    }

    fun checkWinner(): Player? {
        for (i in winnerChecks) {
            val winner = i.checkWinner()
            if (winner != null)
                return winner
        }
        return null
    }

    fun start() {
        resetPlayers()
        started = true
    }

    fun getField() = field

    fun makeTurn(x: Int, y: Int): Boolean {
        if (field[x][y].isFilled())
            return false
        field[x][y].player = getCurrentPlayer()
        cellsFilled++
        switchPlayers()
        return true
    }

    fun switchPlayers() {
        currentPlayer = if (currentPlayer == players[0]) players[1] else players[0]
    }

    private fun resetPlayers() {
        players = arrayOf(
            Player("X"),
            Player("O")
        )
        setCurrentPlayer(players[0])
    }

    fun reset() {
        resetFields()
        resetPlayers()
    }

    fun resetFields() {
        for (arr in field) {
            for (cell in arr) {
                cell.player = null
            }
        }
        cellsFilled = 0
    }

    fun isFieldFilled(): Boolean = cellsCount == cellsFilled

    fun setCurrentPlayer(player: Player) {
        currentPlayer = player
    }

    fun getCurrentPlayer(): Player? = currentPlayer

}