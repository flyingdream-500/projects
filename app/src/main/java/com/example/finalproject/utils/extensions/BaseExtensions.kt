package com.example.finalproject.utils.extensions

import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

object BaseExtensions {

    /**
     * Условие проверки актуальности данных о курсе валют
     */
    fun String.isToday(format: String): Boolean {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val today = sdf.format(Date())
        return this == today
    }

    /**
     * Округление баланса до 3-х знаков
     */
    fun Float.scaleTo3Symbols(): Float =
        toBigDecimal().setScale(3, RoundingMode.HALF_UP).toFloat()


    /**
     * Округление баланса до 2-х знаков
     */
    fun Float.scaleTo2Symbols(roundingMode: RoundingMode): Float {
        return toBigDecimal().setScale(2, roundingMode).toFloat()
    }

    /**
     * Максимальная велечина суммы для базовой валюты
     */
    fun baseRatedBalance(input: Float, rate: Float, roundingMode: RoundingMode): Float {
        val result = input * rate
        return result.scaleTo2Symbols(roundingMode)
    }

    /**
     * Максимальная велечина суммы для целевой валюты
     */
    fun targetRatedBalance(input: Float, rate: Float, roundingMode: RoundingMode): Float {
        val result = input / rate
        return result.scaleTo2Symbols(roundingMode)
    }
}