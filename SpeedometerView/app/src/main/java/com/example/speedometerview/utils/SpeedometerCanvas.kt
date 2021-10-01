package com.example.speedometerview.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.speedometerview.R
import kotlin.math.cos
import kotlin.math.sin


object SpeedometerCanvas {

    fun Canvas.drawCircleLayer(x: Float, y: Float, r: Float, paint: Paint) {
        drawCircle(x, y, r, paint)
    }

    fun Canvas.drawLayerWithoutStroke(x: Float, y: Float, r: Float, paint: Paint) {
        drawCircle(x, y, r - paint.strokeWidth / 2, paint)
    }

    fun Canvas.drawArrow(r: Float, paint: Paint) {
        drawPath(Func.trianglePath(r * 0.05f, r * 0.75f, paint.strokeWidth), paint)
    }

    fun Canvas.rotateSpeed(speed: Int, maxSpeed: Int) {
        rotate(Const.START_ARROW_ANGLE + Const.END_ARROW_ANGLE * (speed / maxSpeed.toFloat()))
    }

    // Drawing AMG logo
    fun Canvas.drawLogo(xp: Float, yp: Float, bitmap: Bitmap,) {
        drawBitmap(
            bitmap,
            xp - bitmap.width / 2,
            yp - bitmap.height * 2.5f,
            null
        )
    }

    // Drawing speed gradient arc on speedometer
    fun Canvas.drawSpeedGradient(arcRect: RectF) {
        drawArc(
            Func.gradientRectangle(SpeedometerPaints.fifthLayerPaint, arcRect),
            Const.START_COUNTDOWN_ANGLE,
            Const.END_COUNTDOWN_ANGLE,
            false,
            SpeedometerPaints.fifthLayerPaint
        )
    }

    // Drawing marks and double marks on speedometer
    fun Canvas.drawMarks(markWidth: Float, markHeight: Float) {
        for (i in 0..Const.COUNT_OF_MARKS) {
            val startX = markWidth * cos(Const.ARC_LENGTH + Const.STEP_OF_MARKS * i).toFloat()
            val startY = markHeight * sin(Const.ARC_LENGTH + Const.STEP_OF_MARKS * i).toFloat()
            var stopY = 0f
            var stopX = 0f
            if (i % 3 == 0) {
                stopY = startY * 1.16f
                stopX = startX * 1.16f
                drawLine(startX, startY, stopX, stopY, SpeedometerPaints.doubleMarkPaint)
            } else {
                stopY = startY * 1.08f
                stopX = startX * 1.08f
                drawLine(startX, startY, stopX, stopY, SpeedometerPaints.markPaint)
            }
        }
    }

    /**
     * Drawing numbers on double marks
     * @see Const.MARKS_PER_NUMBER
     */
    fun Canvas.drawNumbers(textWidth: Float, textHeight: Float, speedPerMark: Int) {
        for (i in 0 until Const.COUNT_OF_NUMBERS) {
            val startX = textWidth * cos(Const.ARC_LENGTH + Const.STEP_OF_MARKS * Const.MARKS_PER_NUMBER * i)
                .toFloat() - SpeedometerPaints.radius * 0.1f
            val startY = textHeight * sin(Const.ARC_LENGTH + Const.STEP_OF_MARKS * Const.MARKS_PER_NUMBER * i)
                .toFloat() + SpeedometerPaints.radius * 0.04f
            drawText(
                (speedPerMark * i * Const.MARKS_PER_NUMBER).toString(),
                startX,
                startY,
                SpeedometerPaints.textPaint
            )
        }
    }
}

fun View.getLogoBitmap(logoColor: Int, radius: Float): Bitmap? {
    return ContextCompat.getDrawable(context, R.drawable.ic_amg_logo)
        .also { it?.setTint(logoColor) }
        ?.toBitmap((radius * 0.66).toInt(), (radius * 0.1f).toInt())
}