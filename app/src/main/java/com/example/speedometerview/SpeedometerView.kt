package com.example.speedometerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
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
        shader = LinearGradient(0f, 0f, 500f, 500f, Color.RED, Color.BLACK, Shader.TileMode.MIRROR)
    }

    private var layer2Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 30f
        //color = Color.RED
        shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR )
    }
    private var layer3Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR )
    }

    private var layer4Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        //strokeWidth = 5f
        color = Color.BLACK
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR )
    }

    private var layer5Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 40f
        //color = Color.BLACK
        shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var layer6Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var externalStrokeWidth = 10f
    private var externalStrokeColor = 0

    private var max: Int


    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val arcRect = RectF(0f, 0f, 900f, 900f)


    init {

        amgBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.amg)
        amgBitmap = amgBitmap.scale(300, 150, false)
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

        canvas?.apply {
            translate(100f , 200f)
            //scale(900f, 900f)
            drawCircle(arcRect.centerX(), arcRect.centerY(), arcRect.centerX(), layer1Paint)
            drawCircle(arcRect.centerX(), arcRect.centerY(), arcRect.centerX()-20, layer2Paint)
            drawCircle(arcRect.centerX(), arcRect.centerY(), arcRect.centerX()-30, layer3Paint)
            drawCircle(arcRect.centerX(), arcRect.centerY(), arcRect.centerX()-35, layer4Paint)
            drawCircle(arcRect.centerX(), arcRect.centerY(), arcRect.centerX()-60, layer5Paint)
            drawBitmap(amgBitmap, 280f, 270f, layer6Paint)
            drawCircle(arcRect.centerX(), arcRect.centerY(), arcRect.centerX()-230, layer6Paint)
            //drawArc(arcRect, TOP_START_ANGLE, FULL_CIRCLE_ANGLE / max * progress, false, foregroundPaint)
            //drawTextProgress()
        }
    }

    fun configurePaint() {
        backgroundPaint.strokeWidth = strokeWidth
        backgroundPaint.color = backgroundClr
        foregroundPaint.strokeWidth = strokeWidth
        foregroundPaint.color = foregroundColor

    }


    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
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
    }*/
}