package com.example.speedometerview.utils

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF

object Func {

    // Rect for speed gradient arc on speedometer
    fun gradientRectangle(paint: Paint, sourceArc: RectF): RectF {
        return RectF(
            paint.strokeWidth / 2 + sourceArc.left,
            paint.strokeWidth / 2 + sourceArc.top,
            sourceArc.right - paint.strokeWidth / 2,
            sourceArc.bottom - paint.strokeWidth / 2
        )
    }

    // Colors for textView speed
    fun getColorBySpeed(speed: Int, maxSpeed: Int): Int {
        return when (speed) {
            in 0 until maxSpeed / 3 -> Color.GREEN
            in maxSpeed / 3 until maxSpeed * 4 / 5 -> Color.YELLOW
            in maxSpeed * 4 / 5 .. maxSpeed -> Color.RED
            else -> Color.WHITE
        }
    }


    fun trianglePath(
        arrowWidth: Float,
        arrowHeight: Float,
        strokeWidth: Float
    ): Path {

        val startX = 0f
        val startY = 0f
        val halfWidth = arrowWidth / 2

        return Path().apply {
            moveTo(startX, startY)
            lineTo(startX + halfWidth + strokeWidth, startY)
            lineTo(startX, arrowHeight)
            lineTo(startX - halfWidth - strokeWidth, startY)
            lineTo(startX + arrowWidth, startY)
        }
    }

}