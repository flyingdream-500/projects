package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.ScaleGestureDetector
import android.view.View
import com.example.drawingapp.model.Drawer
import com.example.drawingapp.model.LinePath
import com.example.drawingapp.model.Sp
import com.example.drawingapp.utils.Const.DEFAULT_BRUSH_WIDTH
import com.example.drawingapp.utils.Const.DEFAULT_COLOR
import java.util.*
import kotlin.math.max
import kotlin.math.min


class DrawingView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var drawnPaths = Stack<LinePath>()
    private var redoPaths = Stack<LinePath>()

    private var map = TreeMap<Int, Sp>()

    var drawingMode = true

    private var currentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = DEFAULT_COLOR
        strokeWidth = DEFAULT_BRUSH_WIDTH
    }
    //private var currentPath = arrayListOf<Path>()
   // private var currentTouch = arrayListOf<PointF>()



    var currentDrawer = Drawer.BRUSH
    set(value) {
        field = value
        drawingMode = value != Drawer.SCALE
    }


    var detector: ScaleGestureDetector? = null


    fun setColor(newColor: Int) {
        currentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = newColor
            strokeWidth = DEFAULT_BRUSH_WIDTH
        }
    }

    var drawCanvas: Canvas? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val canvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        for (linePath in drawnPaths) {
            canvas?.drawPath(linePath.drawPath, linePath.drawPaint)
        }
        map.values.forEach {
            canvas?.drawPath(it.path, currentPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val actionMasked = event?.actionMasked
        val pointerIndex = event?.actionIndex!!
        val pointerId = event.getPointerId(pointerIndex)

        if (drawingMode) {
            when(actionMasked) {
                ACTION_DOWN,
                ACTION_POINTER_DOWN -> {
                    redoPaths.clear()
                    val currentPoint = PointF(event.getX(pointerIndex), event.getY(pointerIndex))
                    val sp = Sp(Path().apply {
                        moveTo(currentPoint.x, currentPoint.y)
                        },
                        currentPoint)
                    map.put(pointerId, sp)
                    return true
                }
                ACTION_MOVE -> {
                    for (i in 0 until event.pointerCount) {
                    val id = event.getPointerId(i)
                    val c = map.get(id)
                        if (c != null) {

                        touchMove(event.getX(i), event.getY(i), c.path, c.point)
                        }
                    }
                }
                ACTION_UP,
                ACTION_POINTER_UP -> {
                    val c = map.get(pointerId)!!
                    touchUp(c.path)
                }
            }
            invalidate()
            return true
        } else {
            detector?.onTouchEvent(event)
            return true
        }
    }

    private fun touchStart(pointF: PointF, path: Path, initialTouch: PointF) {

        path.reset()
        path.moveTo(pointF.x, pointF.y)
        initialTouch.x = pointF.x
        initialTouch.y = pointF.y
    }

    private fun touchMove(x: Float, y: Float, path: Path, pointF: PointF) {
        when(currentDrawer) {
            Drawer.BRUSH -> {
                path.quadTo(pointF.x, pointF.y, (x + pointF.x) / 2, (y + pointF.y) / 2)
                pointF.x = x
                pointF.y = y
                return
            }
            Drawer.LINE -> {
                path.reset()
                path.moveTo(pointF.x, pointF.y)
                path.lineTo(x, y)
                return
            }
            Drawer.RECTANGLE -> {
                path.reset()
                val top = min(pointF.y, y)
                val bottom = max(pointF.y, y)
                val left = min(pointF.x, x)
                val right = max(pointF.x, x)
                path.addRect(left, top, right, bottom, Path.Direction.CCW)
                return
            }
        }
    }

    private fun touchUp(path: Path) {
        drawCanvas?.drawPath(path, currentPaint)
        drawnPaths.push(LinePath(path, currentPaint))
        //currentPath = Path()

    }

    fun reset() {
        drawnPaths.clear()
        redoPaths.clear()
        map.clear()
        invalidate()
    }

    fun undo() {
        if (!drawnPaths.empty()) {
            redoPaths.push(drawnPaths.pop())
            invalidate()
        }
    }
    fun redo() {
        if (!redoPaths.empty()) {
            drawnPaths.push(redoPaths.pop())
            invalidate()
        }
    }
}