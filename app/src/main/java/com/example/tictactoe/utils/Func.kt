package com.example.tictactoe.utils

import android.view.View
import android.widget.Button
import android.widget.TextView

fun main() {

}

object Const {
    const val ROW_COUNT = 3
    const val COL_COUNT = 3

    const val END_GAME_TAG = "game"
    const val END_ROUND_TAG = "round"
}


object Extensions {
    fun Array<Array<Button>>.refresh() {
        for (arr in this) {
            for (b in arr) {
                b.text = ""
            }
        }
    }

    fun TextView.gone() {
        this.visibility = View.GONE
    }

    fun TextView.visible() {
        this.visibility = View.VISIBLE
    }
}
