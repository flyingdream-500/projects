package com.example.drawingapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.ScaleGestureDetector
import android.view.View
import com.example.drawingapp.listener.DrawScaleDetector
import com.example.drawingapp.model.Drawer
import com.example.drawingapp.model.Drawer.*
import com.example.drawingapp.model.LinePath
import com.example.drawingapp.model.MultiLineClass
import com.example.drawingapp.utils.Const.DEFAULT_COLOR
import com.example.drawingapp.utils.createPaint
import java.util.*
import kotlin.math.max
import kotlin.math.min


class DrawingView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    //Стэк для хранения нарисованных линий
    private var drawnPaths = Stack<LinePath>()

    //Стэк для хранения нарисованных линий при возврате назад
    private var redoPaths = Stack<LinePath>()

    //Map для хранения индекса и path рисующихся элементов
    private var map: TreeMap<Int, MultiLineClass>? = TreeMap<Int, MultiLineClass>()

    //Условие для обозначения рисования или масштабирования
    private var drawingMode = true

    //Инициализация кисти
    private var currentPaint = createPaint(DEFAULT_COLOR)
    fun setColor(newColor: Int) {
        currentPaint = createPaint(newColor)
    }

    //Инициализация инструмента рисования
    private var currentDrawer = BRUSH
    fun setDrawer(newDrawer: Drawer) {
        currentDrawer = newDrawer
        drawingMode = currentDrawer != SCALE
    }

    //Инициализация ScaleDetector
    private var detector: ScaleGestureDetector =
        ScaleGestureDetector(context, DrawScaleDetector(this))


    override fun onDraw(canvas: Canvas?) {
        for (linePath in drawnPaths) {
            canvas?.drawPath(linePath.drawPath, linePath.drawPaint)
        }
        map?.let { it ->
            it.values.forEach { multiLine -> canvas?.drawPath(multiLine.path, currentPaint) }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val actionMasked = event!!.actionMasked
        val pointerIndex = event.actionIndex
        val pointerId = event.getPointerId(pointerIndex)
        if (drawingMode) {
            when (actionMasked) {
                ACTION_DOWN,
                ACTION_POINTER_DOWN -> {
                    touchStart(event.getX(pointerIndex), event.getY(pointerIndex), pointerId)
                }
                ACTION_MOVE -> {
                    for (i in 0 until event.pointerCount) {
                        touchMove(event.getPointerId(i), event.getX(i), event.getY(i))
                    }
                }
                ACTION_POINTER_UP -> {
                    touchUp(pointerId)
                }
                ACTION_UP -> {
                    touchUp(pointerId)
                    map?.clear()
                }
            }
            invalidate()
            return true
        } else {
            detector.onTouchEvent(event)
            return true
        }
    }

    // Определяем точку начала рисования и добавляем ее в map
    private fun touchStart(touchX: Float, touchY: Float, id: Int) {
        redoPaths.clear()
        val touchPoint = PointF(touchX, touchY)
        val multiLine = MultiLineClass(
            Path().also { it.moveTo(touchPoint.x, touchPoint.y) },
            touchPoint
        )
        map?.put(id, multiLine)
    }

    // Рисоуем path в зависимости от выбранного инструмента
    private fun touchMove(id: Int, moveX: Float, moveY: Float) {
        val multiLine = map?.get(id)

        if (multiLine != null) {
            val path = multiLine.path
            val pointF = multiLine.point

            when (currentDrawer) {
                BRUSH -> {
                    path.quadTo(pointF.x, pointF.y, (moveX + pointF.x) / 2, (moveY + pointF.y) / 2)
                    pointF.x = moveX
                    pointF.y = moveY
                }
                LINE -> {
                    path.reset()
                    path.moveTo(pointF.x, pointF.y)
                    path.lineTo(moveX, moveY)
                }
                RECTANGLE -> {
                    path.reset()
                    val top = min(pointF.y, moveY)
                    val bottom = max(pointF.y, moveY)
                    val left = min(pointF.x, moveX)
                    val right = max(pointF.x, moveX)
                    path.addRect(left, top, right, bottom, Path.Direction.CCW)
                }
            }
        }


    }

    //Добавляем нарисованный элемент в стэк
    private fun touchUp(id: Int) {
        val multiLine = map?.get(id)
        if (multiLine != null) {
            drawnPaths.push(LinePath(multiLine.path, currentPaint))
        }
    }

    //Очистка канваса
    fun reset() {
        drawnPaths.clear()
        redoPaths.clear()
        map?.clear()
        invalidate()
    }

    //Убрать поледний элемент в стэк
    fun undo() {
        if (!drawnPaths.empty()) {
            redoPaths.push(drawnPaths.pop())
            invalidate()
        }
    }

    //Вернуть последний элемент из стэка
    fun redo() {
        if (!redoPaths.empty()) {
            drawnPaths.push(redoPaths.pop())
            invalidate()
        }
    }
}