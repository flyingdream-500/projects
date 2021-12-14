package com.example.speedometerview.utils

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.SweepGradient
import androidx.core.content.res.ResourcesCompat
import com.example.speedometerview.R


object SpeedometerPaints {

    var radius = 0f
    var paddingDiff = 0
    var positions = floatArrayOf(0f, 0.5f, 1f)

    /**
     * Paints
     */
    lateinit var panelPaint: Paint
    lateinit var firstLayerPaint: Paint
    lateinit var secondLayerPaint: Paint
    lateinit var thirdLayerPaint: Paint
    lateinit var fourthLayerPaint: Paint
    lateinit var fifthLayerPaint: Paint
    lateinit var sixthLayerPaint: Paint
    lateinit var centralLayerPaint: Paint
    lateinit var markPaint: Paint
    lateinit var doubleMarkPaint: Paint
    lateinit var textPaint: Paint
    lateinit var arrowPaint: Paint
    lateinit var arrowStrokePaint: Paint


    fun createPanelPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = newColor
    }

    fun createFirstLayerPaint(colorsArray: IntArray) =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = radius * 0.02f
            shader =
                LinearGradient(
                    0f, 0f,
                    radius + paddingDiff,
                    radius + paddingDiff,
                    colorsArray,
                    positions,
                    Shader.TileMode.MIRROR
                )
        }

    fun createSecondLayerPaint(colorsArray: IntArray) =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = radius * 0.08f
            shader =
                LinearGradient(
                    0f, 0f,
                    radius + paddingDiff,
                    radius + paddingDiff,
                    colorsArray,
                    positions,
                    Shader.TileMode.MIRROR
                )
        }

    fun createThirdLayerPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.1f
        color = newColor
    }

    fun createFourthLayerPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.11f
        color = newColor
    }

    fun createFifthLayerPaint(colorsArray: IntArray) =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = radius * 0.23f
            shader = SweepGradient(
                radius + paddingDiff,
                radius + paddingDiff,
                colorsArray,
                floatArrayOf(0f, 0.4f, 1f)
            )
        }


    fun createHalfRadiusCirclePaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.005f
        color = newColor
    }

    fun createCentralLayerPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.01f
        color = newColor
    }


    fun createMarkPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.02f
        color = newColor
    }

    fun createDoubleMarkPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.04f
        color = newColor
    }

    fun createTextPaint(context: Context, newColor: Int) =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            typeface = ResourcesCompat.getFont(context, R.font.enter_the_grid)
            textSize = radius * 0.12f
            color = newColor
        }

    fun createArrowPaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = radius * 0.02f
        color = newColor
    }

    fun createArrowStrokePaint(newColor: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = radius * 0.02f
        color = newColor
    }


}