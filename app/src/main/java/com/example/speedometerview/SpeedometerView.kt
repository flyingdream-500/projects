package com.example.speedometerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
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
        //color = Color.WHITE
        alpha = 255
        shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.LTGRAY, Shader.TileMode.MIRROR )
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
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var doubleMarkPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = ResourcesCompat.getFont(this@SpeedometerView.context, R.font.enter_the_grid)
        textSize = 60f
        color = Color.WHITE
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var arrowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 10f
        color = Color.RED
        //shader = LinearGradient(0f, 0f, 500f, 500f, Color.BLACK, Color.DKGRAY, Shader.TileMode.CLAMP )
    }

    private var arrowPaint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
        strokeWidth = 10f
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

        val arcRect = RectF(-width/2 + 50f, -height + 50f, width/2 - 50f, height - 50f)

        canvas?.apply {
            translate(width/2 , height)

            //canvas.scale(width*1.1.toFloat(), height*0.9.toFloat())
            //drawOval(0f,0f,height*2.toFloat(), width, layer5Paint)

            val xp = 0f
            val yp = 0f
            val r = width/2

            drawCircle(xp, yp, r, layerBackgroundPaint)
            drawBitmap(amgBitmap, -width*0.17f, -height*0.4f, layer6Paint)
            drawCircle(xp, yp, r/2, layer6Paint)
            //drawCircle(xp, yp, r - layer5Paint.strokeWidth / 2, layer5Paint)
            drawArc(arcRect, 60f, -300f, false, layer5Paint)
            drawCircle(xp, yp, r - layer3aPaint.strokeWidth / 2, layer3aPaint)
            drawCircle(xp, yp, r - layer3Paint.strokeWidth / 2, layer3Paint)
            drawCircle(xp, yp, r - layer2Paint.strokeWidth / 2, layer2Paint)
            drawCircle(xp, yp, r - layer1Paint.strokeWidth / 2, layer1Paint)
            drawCircle(xp, yp, 50f, layer1Paint)


            val maxValue = 30
            val value = 25
            val all = 5 * Math.PI / 3
            val step = all/maxValue
            val wdt = width/2 - 100f
            val hgt = height - 100f

            for (i in 0 .. maxValue) {
                val startX1 = 0f - wdt * Math.cos(all + step*i).toFloat()
                val startY1 = 0f - hgt * Math.sin(all + step*i).toFloat()
                var stopY1 = 0f
                var stopX1 = 0f
                if (i % 3 == 0) {
                    stopY1 = startY1*1.13f
                    stopX1 = startX1*1.13f
                    drawLine(startX1, startY1, stopX1, stopY1, doubleMarkPaint)
                } else {
                    stopY1 = startY1*1.08f
                    stopX1 = startX1*1.08f
                    drawLine(startX1, startY1, stopX1, stopY1, markPaint)
                }

            }

            val textWdt = width/2 - 180f
            val textHgt = height - 150f

            val textCount = 11

            for (i in 0 until  textCount) {
                val startX1 = -50f - textWdt * Math.cos(all + step*3*i).toFloat()
                val startY1 = 20f - textHgt * Math.sin(all + step*3*i).toFloat()
                drawText((10 * i * 3).toString(), startX1, startY1, textPaint )

                //drawPath(trianglePath(0f, 0f, 30f, height * Math.sin(all + step*3*i).toFloat(), width / 2 * Math.cos(all + step*3*i).toFloat(), 0f), arrowPaint)
                //drawPath(trianglePath(0f, 0f, 30f, height * Math.sin(all + step*3*i).toFloat(), width / 2 * Math.cos(all + step*3*i).toFloat(), arrowPaint2.strokeWidth), arrowPaint2)
            }

            //drawLine(0f, 0f,-textHgt + 200f, textWdt, arrowPaint)
            drawPath(trianglePath(0f, 0f, 30f, height - 120f, -height - 120f, 0f), arrowPaint)
            drawPath(trianglePath(0f, 0f, 30f, height - 120f, -height - 120f,  arrowPaint2.strokeWidth), arrowPaint2)
            //drawTriangle(this, arrowPaint, 0, 0, 30)
        }


    }

    fun trianglePath(x: Float, y: Float, width: Float, h: Float, w: Float, strokeWidth: Float): Path {
        val halfWidth = width / 2

        /*return Path().apply {
            moveTo(h, w) //top
            lineTo(x - width, y) // bottom left
            moveTo(h, w) //top
            lineTo(x + width, y) // bottom right
            moveTo(x, y)
            lineTo(x + width + strokeWidth/2, y) // back to top
            lineTo(x - width - strokeWidth/2, y) // back to top
            //close()
        }*/

        return Path().apply {
            moveTo(x, y) //top
            lineTo(x + halfWidth + strokeWidth, y) // bottom left
            //moveTo(h, w) //top
            lineTo(x, h) // bottom right
            //moveTo(x, y)
            lineTo(x - halfWidth - strokeWidth, y) // back to top
            lineTo(x + width, y) // back to top
            //close()
        }
    }

    fun drawTriangle(canvas: Canvas, paint: Paint?, x: Int, y: Int, width: Int) {
        val halfWidth = width / 2
        val path = Path()
        path.moveTo(x.toFloat(), (y - halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom left
        path.lineTo((x + halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom right
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()) // Back to Top
        path.close()
        canvas.drawPath(path, paint!!)
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