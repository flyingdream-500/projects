package com.example.drawingapp.model

import android.graphics.Paint
import android.graphics.Path

/**
 * Класс с информацией о виде нарисованной фигуры и цвета
 */
data class LinePath(
    val drawPath: Path,
    val drawPaint: Paint
)