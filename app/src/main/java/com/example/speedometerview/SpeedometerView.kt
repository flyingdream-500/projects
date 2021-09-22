package com.example.speedometerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.scale

class SpeedometerView(
    context: Context,
    attributeSet: AttributeSet,
) : View(context, attributeSet) {

    @ColorInt
    private var backgroundClr: Int = 0

    @ColorInt
    private var foregroundColor: Int = 0

    private var strokeWidth: Float

    private var amgBitmap: Bitmap

    private var layer1Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.RED
        shader = LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), Color.RED, Color.BLACK, Shader.TileMode.MIRROR)
    }

    private var layer2Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 30f
        //color = Color.RED
        shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.GRAY, Shader.TileMode.MIRROR )
    }
    private var layer3Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        color = Color.BLACK
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR )
    }
    private var layer3aPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 45f
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR )
    }

    private var layerBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR )
    }

    private var layer5Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 100f
        shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var layer6Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var markPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.RED
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }


    private var externalStrokeWidth = 10f
    private var externalStrokeColor = 0



    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val arcRect = RectF(0f, 0f, 1000f, 1000f)


    private var max: Int = 33
    private var currentValue = 30
    private val step = Math.PI / max


    init {

        amgBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.amg)
        amgBitmap = amgBitmap.scale(350, 120, false)
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.SpeedometerView,
            R.attr.speedometerDefaultAttr,
            0
        )

        try {
            backgroundClr = typedArray.getColor(R.styleable.SpeedometerView_backgroundClr   , 0)
            foregroundColor = typedArray.getColor(R.styleable.SpeedometerView_foregroundColor, 0)
            max = typedArray.getInt(R.styleable.SpeedometerView_max, 0)
            strokeWidth = typedArray.getDimension(R.styleable.SpeedometerView_strokeWidth, 0f)
        } finally {
            typedArray.recycle()
        }

        configurePaint()
    }

    override fun onDraw(canvas: Canvas?) {

        var width = width.toFloat()
        var height = height.toFloat()

        val aspect = width / height
        val normalAspect = 2f / 1f
        if (aspect > normalAspect) {
            width = normalAspect * height
        }
        if (aspect < normalAspect) {
            height = width / normalAspect
        }

        val arcRect = RectF(50f, 50f, width - 50f, height*2 - 50f)

        canvas?.apply {
            translate(0f , 100f)

            //canvas.scale(width*1.1.toFloat(), height*0.9.toFloat())
            //drawOval(0f,0f,height*2.toFloat(), width, layer5Paint)

            val xp = width/2
            val yp = height
            val r = width/2

            drawCircle(xp, yp, r, layerBackgroundPaint)
            drawBitmap(amgBitmap, xp*0.65f, yp*0.65f, layer6Paint)
            drawCircle(xp, yp, r/2, layer6Paint)
            //drawCircle(xp, yp, r - layer5Paint.strokeWidth / 2, layer5Paint)
            drawArc(arcRect, 60f, -300f, false, layer5Paint)
            drawCircle(xp, yp, r - layer3aPaint.strokeWidth / 2, layer3aPaint)
            drawCircle(xp, yp, r - layer3Paint.strokeWidth / 2, layer3Paint)
            drawCircle(xp, yp, r - layer2Paint.strokeWidth / 2, layer2Paint)
            drawCircle(xp, yp, r - layer1Paint.strokeWidth / 2, layer1Paint)
            drawCircle(xp, yp, 50f, layer1Paint)


            val maxValue = 10
            val value = 25
            val step = Math.PI/maxValue

            for (i in 0 .. maxValue) {
                val startX1 = xp*1f + xp * Math.cos(Math.PI + step*i).toFloat()
                val startY1 = yp*1.1f + yp * Math.sin(Math.PI + step*i).toFloat()
                val stopX1 = startX1*1.1f
                val stopY1 = startY1*1.1f

                drawLine(startX1, startY1, stopX1, stopY1, markPaint)
            }

            //drawLine(xp/2, yp*1.8f, xp/2 + 50f, yp*1.8f - 50f, markPaint)
            //drawLine(xp, yp, xp + xp * Math.cos(30.0).toFloat(), yp * 2, markPaint)

            /*val maxValue = 120
            val value = 25

            val scale = 0.9f

            val step = Math.PI / maxValue
            for (i in 0..maxValue) {
                val x1 = xp + Math.cos(Math.PI - step * i).toFloat()
                val y1 = yp + Math.sin(Math.PI - step * i).toFloat()
                var x2: Float
                var y2: Float
                if (i % 20 == 0) {
                    x2 = x1 * scale * 0.9f
                    y2 = y1 * scale * 0.9f
                } else {
                    x2 = x1 * scale
                    y2 = y1 * scale
                }
                canvas.drawLine(x1, y1, x2, y2, markPaint)
            }*/
        }


    }

    fun configurePaint() {
        backgroundPaint.strokeWidth = strokeWidth
        backgroundPaint.color = backgroundClr
        foregroundPaint.strokeWidth = strokeWidth
        foregroundPaint.color = foregroundColor

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        val aspect = width / height.toFloat()
        val normalAspect = 2f / 1f
        if (aspect > normalAspect) {
            if (widthMode != MeasureSpec.EXACTLY) {
                width = Math.round(normalAspect * height)
            }
        }
        if (aspect < normalAspect) {
            if (heightMode != MeasureSpec.EXACTLY) {
                height = Math.round(width / normalAspect)
            }
        }
        setMeasuredDimension(width, height)
    }
}