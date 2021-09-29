package com.example.drawingapp.model

import android.graphics.Path
import android.graphics.PointF

/**
 * Класс для экземпляра мультитача с сохранением координат и вида нарисованной фигуры
 */
data class MultiLineClass(
    val path: Path,
    val point: PointF
)