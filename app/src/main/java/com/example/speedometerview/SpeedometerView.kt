package com.example.speedometerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.example.speedometerview.utils.*
import com.example.speedometerview.utils.Const.COUNT_OF_MARKS
import com.example.speedometerview.utils.Const.MIN_WRAP
import com.example.speedometerview.utils.SpeedometerPaints.arrowPaint
import com.example.speedometerview.utils.SpeedometerPaints.createArrowPaint
import com.example.speedometerview.utils.SpeedometerPaints.arrowStrokePaint
import com.example.speedometerview.utils.SpeedometerPaints.createArrowStrokePaint
import com.example.speedometerview.utils.SpeedometerPaints.panelPaint
import com.example.speedometerview.utils.SpeedometerPaints.createPanelPaint
import com.example.speedometerview.utils.SpeedometerPaints.centralLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createCentralLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.doubleMarkPaint
import com.example.speedometerview.utils.SpeedometerPaints.createDoubleMarkPaint
import com.example.speedometerview.utils.SpeedometerPaints.fifthLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createFifthLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.firstLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createFirstLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.fourthLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createFourthLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.markPaint
import com.example.speedometerview.utils.SpeedometerPaints.createMarkPaint
import com.example.speedometerview.utils.SpeedometerPaints.paddingDiff
import com.example.speedometerview.utils.SpeedometerPaints.radius
import com.example.speedometerview.utils.SpeedometerPaints.secondLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createSecondLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.sixthLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createHalfRadiusCirclePaint
import com.example.speedometerview.utils.SpeedometerPaints.textPaint
import com.example.speedometerview.utils.SpeedometerPaints.createTextPaint
import com.example.speedometerview.utils.SpeedometerPaints.thirdLayerPaint
import com.example.speedometerview.utils.SpeedometerPaints.createThirdLayerPaint
import com.example.speedometerview.utils.SpeedometerCanvas.drawArrow
import com.example.speedometerview.utils.SpeedometerCanvas.drawCircleLayer
import com.example.speedometerview.utils.SpeedometerCanvas.drawLayerWithoutStroke
import com.example.speedometerview.utils.SpeedometerCanvas.drawLogo
import com.example.speedometerview.utils.SpeedometerCanvas.drawMarks
import com.example.speedometerview.utils.SpeedometerCanvas.drawNumbers
import com.example.speedometerview.utils.SpeedometerCanvas.drawSpeedGradient
import com.example.speedometerview.utils.SpeedometerCanvas.rotateSpeed
import kotlin.math.max
import kotlin.math.min

class SpeedometerView(
    context: Context,
    attributeSet: AttributeSet,
) : View(context, attributeSet) {

    /**
     * Attributes
     */
    // Background panel color
    @ColorInt
    private var panelColor: Int

    // External circle gradient color
    @ColorInt
    private var firstLayerGradientStart: Int

    @ColorInt
    private var firstLayerGradientCenter: Int

    @ColorInt
    private var firstLayerGradientEnd: Int

    // Second external circle gradient color
    @ColorInt
    private var secondLayerGradientStart: Int

    @ColorInt
    private var secondLayerGradientCenter: Int

    @ColorInt
    private var secondLayerGradientEnd: Int

    // Border circle #1 gradient color
    @ColorInt
    private var thirdLayerColor: Int

    // Border circle #2 gradient color
    @ColorInt
    private var fourthLayerColor: Int

    // Gradient color above speedometer marks
    @ColorInt
    private var arcGradientStart: Int

    @ColorInt
    private var arcGradientCenter: Int

    @ColorInt
    private var arcGradientEnd: Int

    // Color of half radius central circle
    @ColorInt
    private var halfRadiusCircleColor: Int

    // Color of  central circle
    @ColorInt
    private var centralCircleColor: Int

    // Inner color of arrow
    @ColorInt
    private var arrowColor: Int

    // External color of arrow
    @ColorInt
    private var arrowStrokeColor: Int

    // Mark color of speedometer
    @ColorInt
    private var markColor: Int

    // Double Mark color of speedometer
    @ColorInt
    private var doubleMarkColor: Int

    // Numbers color
    @ColorInt
    private var numbersColor: Int

    // AMG logo color
    @ColorInt
    private var logoColor: Int

    //Max speed in speedometer appearance
    private var maxSpeed: Int
    fun getMaxSpeed() = maxSpeed

    //Current speed in speedometer appearance
    private var currentSpeed: Int
    fun getCurrentSpeed() = currentSpeed
    fun setSpeed(newSpeed: Int) {
        currentSpeed = newSpeed
        invalidate()
    }

    // Logo bitmap
    private var amgBitmap: Bitmap? = null

    //Params
    private val arcRect = RectF()
    private var xp = 0f
    private var yp = 0f

    private var markWidth = 0f
    private var markHeight = 0f

    private var textWidth = 0f
    private var textHeight = 0f

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.SpeedometerView,
            R.attr.speedometerDefaultAttr,
            0
        )

        try {
            panelColor = typedArray.getColor(R.styleable.SpeedometerView_panelColor, 0)

            arcGradientStart =
                typedArray.getColor(R.styleable.SpeedometerView_arcGradientStart, 0)
            arcGradientCenter =
                typedArray.getColor(R.styleable.SpeedometerView_arcGradientCenter, 0)
            arcGradientEnd =
                typedArray.getColor(R.styleable.SpeedometerView_arcGradientEnd, 0)

            firstLayerGradientStart =
                typedArray.getColor(R.styleable.SpeedometerView_firstLayerGradientStart, 0)
            firstLayerGradientCenter =
                typedArray.getColor(R.styleable.SpeedometerView_firstLayerGradientCenter, 0)
            firstLayerGradientEnd =
                typedArray.getColor(R.styleable.SpeedometerView_firstLayerGradientEnd, 0)

            secondLayerGradientStart =
                typedArray.getColor(R.styleable.SpeedometerView_secondLayerGradientStart, 0)
            secondLayerGradientCenter =
                typedArray.getColor(R.styleable.SpeedometerView_secondLayerGradientCenter, 0)
            secondLayerGradientEnd =
                typedArray.getColor(R.styleable.SpeedometerView_secondLayerGradientEnd, 0)

            thirdLayerColor =
                typedArray.getColor(R.styleable.SpeedometerView_thirdLayerColor, 0)

            fourthLayerColor =
                typedArray.getColor(R.styleable.SpeedometerView_fourthLayerColor, 0)

            halfRadiusCircleColor =
                typedArray.getColor(R.styleable.SpeedometerView_halfRadiusCircleColor, 0)

            centralCircleColor =
                typedArray.getColor(R.styleable.SpeedometerView_centralCircleColor, 0)

            arrowColor = typedArray.getColor(R.styleable.SpeedometerView_arrowColor, 0)
            arrowStrokeColor =
                typedArray.getColor(R.styleable.SpeedometerView_arrowStrokeColor, 0)

            markColor = typedArray.getColor(R.styleable.SpeedometerView_markColor, 0)
            doubleMarkColor =
                typedArray.getColor(R.styleable.SpeedometerView_doubleMarkColor, 0)

            numbersColor =
                typedArray.getColor(R.styleable.SpeedometerView_numbersColor, 0)

            logoColor = typedArray.getColor(R.styleable.SpeedometerView_logoColor,0)
            maxSpeed = typedArray.getInt(R.styleable.SpeedometerView_maxSpeed, 0)
            currentSpeed = typedArray.getInt(R.styleable.SpeedometerView_currentSpeed, 0)

        } finally {
            typedArray.recycle()
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        val size = min(w, h).toFloat()
        arcRect.set(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            size - paddingRight,
            size - paddingBottom
        )

        // init parameters
        initParams()
    }

    private fun initParams() {
        radius = arcRect.height() / 2
        paddingDiff = (paddingLeft + paddingRight) / 2
        xp = arcRect.centerX()
        yp = arcRect.centerY()

        markWidth = -radius * 0.77f
        markHeight = -radius * 0.77f

        textWidth = -radius * 0.6f
        textHeight = -radius * 0.65f

        amgBitmap = getLogoBitmap(logoColor, radius)

        configurePaints()
    }

    private fun configurePaints() {

        panelPaint = createPanelPaint(panelColor)
        firstLayerPaint = createFirstLayerPaint(
            colorsArray = intArrayOf(
                firstLayerGradientEnd,
                firstLayerGradientStart,
                firstLayerGradientCenter
            )
        )
        secondLayerPaint = createSecondLayerPaint(
            colorsArray = intArrayOf(
                secondLayerGradientEnd,
                secondLayerGradientStart,
                secondLayerGradientCenter
            )
        )
        thirdLayerPaint = createThirdLayerPaint(thirdLayerColor)
        fourthLayerPaint = createFourthLayerPaint(fourthLayerColor)
        fifthLayerPaint = createFifthLayerPaint(
            colorsArray = intArrayOf(
                arcGradientEnd,
                arcGradientStart,
                arcGradientCenter
            )
        )
        sixthLayerPaint = createHalfRadiusCirclePaint(halfRadiusCircleColor)
        centralLayerPaint = createCentralLayerPaint(centralCircleColor)
        markPaint = createMarkPaint(markColor)
        doubleMarkPaint = createDoubleMarkPaint(doubleMarkColor)
        textPaint = createTextPaint(context, numbersColor)
        arrowPaint = createArrowPaint(arrowColor)
        arrowStrokePaint = createArrowStrokePaint(arrowStrokeColor)
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.apply {

            // draw background panel
            drawCircleLayer(xp, yp, radius, panelPaint)

            // draw logo
            amgBitmap?.let {
                drawLogo(xp, yp, it)
            }

            // draw speed controller gradient arc
            drawSpeedGradient(arcRect)

            // draw external circles
            drawLayerWithoutStroke(xp, yp, radius, fourthLayerPaint)
            drawLayerWithoutStroke(xp, yp, radius, thirdLayerPaint)
            drawLayerWithoutStroke(xp, yp, radius, secondLayerPaint)
            drawLayerWithoutStroke(xp, yp, radius, firstLayerPaint)

            // draw central circles
            drawCircleLayer(xp, yp, radius * 0.5f, sixthLayerPaint)
            drawCircleLayer(xp, yp, radius * 0.1f, centralLayerPaint)


            translate(
                radius + (paddingLeft + paddingRight) / 2,
                radius + (paddingTop + paddingBottom) / 2
            )

            // draw marks and double marks
            drawMarks(markWidth, markHeight)

            // draw numbers
            val speedPerMark = maxSpeed / COUNT_OF_MARKS.toFloat()
            drawNumbers(textWidth, textHeight, speedPerMark)

            //rotate
            rotateSpeed(currentSpeed, maxSpeed)

            // draw arrow and his stroke
            drawArrow(radius, arrowPaint)
            drawArrow(radius, arrowStrokePaint)

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val requestedWidth =
            max(MIN_WRAP + paddingLeft + paddingRight, suggestedMinimumWidth)
        val requestedHeight =
            max(MIN_WRAP + paddingTop + paddingBottom, suggestedMinimumHeight)

        val requestedSize = (max(requestedWidth, requestedHeight)).toInt()
        setMeasuredDimension(
            resolveSizeAndState(requestedSize, widthMeasureSpec, 0),
            resolveSizeAndState(requestedSize, heightMeasureSpec, 0)
        )
    }

}