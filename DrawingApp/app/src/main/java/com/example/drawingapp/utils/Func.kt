package com.example.drawingapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import com.example.drawingapp.DrawingView
import com.example.drawingapp.DrawingView.*
import com.example.drawingapp.R
import com.example.drawingapp.model.Drawer.*
import com.example.drawingapp.utils.Const.DEFAULT_COLOR
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlin.reflect.KFunction1

/**
 * Константы
 */
object Const {
    const val MIN_SCALE = .5f
    const val MAX_SCALE = 5f
    const val DEFAULT_COLOR = Color.BLACK
    const val DEFAULT_BRUSH_WIDTH = 25f
}


/**
 * Расширения
 */
object Extensions {

    private fun BottomNavigationView.getBadger() = getOrCreateBadge(R.id.pick_color)

    //Инициализация баджера цвета над иконкой выбора цвета
    fun BottomNavigationView.initBadger() {
        val badge = getBadger()
        badge.backgroundColor = DEFAULT_COLOR
    }

    //Listener выбора инструмента рисования
    fun BottomNavigationView.clickListener(drawingView: DrawingView) {
        setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.brush -> {
                    drawingView.setDrawer(BRUSH)
                }
                R.id.line -> {
                    drawingView.setDrawer(LINE)
                }
                R.id.rectangle -> {
                    drawingView.setDrawer(RECTANGLE)
                }
                R.id.scale -> {
                    drawingView.setDrawer(SCALE)
                }
                R.id.pick_color -> {
                    launchColorPickerDialog(
                        context = context,
                        setDrawingColor = drawingView::setColor,
                        setBadgeColor = getBadger()::setBackgroundColor
                    )
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}


//Отображение диалога с выбором цвета
fun launchColorPickerDialog(
    context: Context,
    setDrawingColor: KFunction1<Int, Unit>,
    setBadgeColor: KFunction1<Int, Unit>
) {
    ColorPickerDialog.Builder(context)
        .setTitle(R.string.color_picker_title)
        .setPositiveButton(R.string.color_picker_positive, object : ColorEnvelopeListener {
            override fun onColorSelected(
                envelope: ColorEnvelope?,
                fromUser: Boolean
            ) {
                val color = envelope?.color ?: DEFAULT_COLOR
                setDrawingColor(color)
                setBadgeColor(color)
            }
        })
        .setNegativeButton(
            R.string.color_picker_negative
        ) { dialog, _ -> dialog?.dismiss() }
        .show()
}

//Создание новой кисти с указанным цветом
fun createPaint(newColor: Int): Paint {
    return Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = newColor
        strokeWidth = Const.DEFAULT_BRUSH_WIDTH
    }
}
