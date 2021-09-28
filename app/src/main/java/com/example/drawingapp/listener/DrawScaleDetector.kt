package com.example.drawingapp.listener

import android.graphics.Color
import android.view.ScaleGestureDetector
import com.example.drawingapp.DrawingView
import com.example.drawingapp.utils.Const.MAX_SCALE
import com.example.drawingapp.utils.Const.MIN_SCALE
import kotlin.math.max
import kotlin.math.min

class DrawScaleDetector(private val drawing: DrawingView) :
    ScaleGestureDetector.SimpleOnScaleGestureListener() {

    var scaleFactor = 1f

    override fun onScale(detector: ScaleGestureDetector?): Boolean {

        val additive = detector!!.scaleFactor - 1

        scaleFactor += additive
        scaleFactor = max(MIN_SCALE, min(scaleFactor, MAX_SCALE))
        drawing.scaleX = scaleFactor
        drawing.scaleY = scaleFactor

        return super.onScale(detector)
    }


}