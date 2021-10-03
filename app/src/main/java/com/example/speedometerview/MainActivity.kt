package com.example.speedometerview

import android.animation.ArgbEvaluator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addPauseListener
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import kotlin.math.min


class MainActivity : AppCompatActivity() {

    private lateinit var speedometerView: SpeedometerView
    private lateinit var tvCurrentSpeed: TextView
    private lateinit var rootLayout: ConstraintLayout

    private val SPEED_KEY = "speed"
    private val GRADIENT_KEY = "gradient"
    private val SCALE_KEY = "scale"
    private val ANIM_DURATION = 5_000L
    private val ANIM_REPEAT_MODE = ValueAnimator.REVERSE
    private val ANIM_REPEAT_COUNT = 1

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometerView = findViewById<SpeedometerView>(R.id.speedometer_view)
        tvCurrentSpeed = findViewById<TextView>(R.id.currentSpeed)
        rootLayout = findViewById<ConstraintLayout>(R.id.constraint_layout)

        val anim = getAnim()
        tvCurrentSpeed.setOnClickListener {
            animate(anim)
        }
    }

    private fun animate(anim: ValueAnimator) {
        if (!anim.isRunning) {
            anim.start()
        } else {
            if (!anim.isPaused) {
                anim.pause()
            } else {
                anim.resume()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getAnim(): ValueAnimator {
        // Colors
        val colorStart = ContextCompat.getColor(this, R.color.green)
        val colorCenter = ContextCompat.getColor(this, R.color.yellow)
        val colorEnd = ContextCompat.getColor(this, R.color.red)

        // Speed
        val maxSpeed = speedometerView.getMaxSpeed().toFloat()
        val currentSpeed = speedometerView.getCurrentSpeed().toFloat()
        val halfPosition = (maxSpeed - currentSpeed) / 2

        // PropertyValuesHolders
        val gradientHolder = PropertyValuesHolder
            .ofObject(GRADIENT_KEY,ArgbEvaluator(), colorStart, colorCenter, colorEnd)
        val speedHolder = PropertyValuesHolder
            .ofFloat(SPEED_KEY, currentSpeed, halfPosition, maxSpeed)
        val scaleHolder = PropertyValuesHolder
            .ofFloat(SCALE_KEY,1f, 1.25f, 1.5f )

        return ValueAnimator.ofPropertyValuesHolder(gradientHolder, speedHolder, scaleHolder).apply {
            duration = ANIM_DURATION
            repeatMode = ANIM_REPEAT_MODE
            repeatCount = ANIM_REPEAT_COUNT
            addUpdateListener { animation ->

                val color =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        animation.getAnimatedValue(GRADIENT_KEY) as Int
                    else Color.WHITE

                val speed = animation.getAnimatedValue(SPEED_KEY) as Float

                val scale = animation.getAnimatedValue(SCALE_KEY) as Float

                speedometerView.setSpeed(speed.toInt())

                //val color = getColorBySpeed(speed.toInt(), view.getMaxSpeed())
                val speedText = String.format(resources.getString(R.string.current_speed), speed.toInt())
                val speedSpan = SpannableString(speedText)
                speedSpan.setSpan(
                    ForegroundColorSpan(color),
                    speedText.indexOf(":") + 1,
                    speedText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tvCurrentSpeed.text = speedSpan

                tvCurrentSpeed.scaleX = scale
                tvCurrentSpeed.scaleY = scale

            }
            doOnEnd {
                tvCurrentSpeed.text = resources.getString(R.string.start)
            }
        }
    }



}