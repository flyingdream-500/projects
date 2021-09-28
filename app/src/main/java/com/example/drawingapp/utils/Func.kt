package com.example.drawingapp.utils

import android.content.Context
import android.graphics.Color
import android.view.ScaleGestureDetector
import com.example.drawingapp.DrawingView
import com.example.drawingapp.DrawingView.*
import com.example.drawingapp.R
import com.example.drawingapp.listener.DrawScaleDetector
import com.example.drawingapp.model.Drawer
import com.example.drawingapp.utils.Const.DEFAULT_COLOR
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

object Const {

    const val MIN_SCALE = .5f
    const val MAX_SCALE = 5f
    const val DEFAULT_COLOR = Color.BLACK
    const val DEFAULT_BRUSH_WIDTH = 25f

}

object Extensions {

    private fun BottomNavigationView.getBadger() = getOrCreateBadge(R.id.pick_color)

    fun BottomNavigationView.initBadger() {
        val badge = getBadger()
        badge.backgroundColor = DEFAULT_COLOR
    }

    fun BottomNavigationView.clickListener(drawingView: DrawingView) {
        val scaleDetector =
            ScaleGestureDetector(context, DrawScaleDetector(drawingView))

        setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.brush -> {
                    drawingView.currentDrawer = Drawer.BRUSH
                }
                R.id.line -> {
                    drawingView.currentDrawer = Drawer.LINE
                }
                R.id.rectangle -> {
                    drawingView.currentDrawer = Drawer.RECTANGLE
                }
                R.id.scale -> {
                    drawingView.currentDrawer = Drawer.SCALE
                    drawingView.detector = scaleDetector
                    //drawingView.setScaleListener(scaleDetector)
                }
                R.id.pick_color -> {
                    launchColorPickerDialog(
                        context = context,
                        drawingView = drawingView,
                        badge = getBadger()
                    )
                }

            }
            return@setOnItemSelectedListener true
        }
    }
}

fun launchColorPickerDialog(context: Context, drawingView: DrawingView, badge: BadgeDrawable) {
    ColorPickerDialog.Builder(context)
        .setTitle(R.string.color_picker_title)
        .setPositiveButton(R.string.color_picker_positive, object : ColorEnvelopeListener {
            override fun onColorSelected(
                envelope: ColorEnvelope?,
                fromUser: Boolean
            ) {
                val color = envelope?.color ?: DEFAULT_COLOR
                drawingView.setColor(color)
                badge.backgroundColor = color
            }
        })
        .setNegativeButton(
            R.string.color_picker_negative
        ) { dialog, _ -> dialog?.dismiss() }
        .show()
}