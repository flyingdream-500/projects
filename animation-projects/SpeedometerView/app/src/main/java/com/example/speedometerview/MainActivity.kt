package com.example.speedometerview

import android.animation.ArgbEvaluator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.example.speedometerview.utils.SpeedAnimatorConst.ANIM_DURATION
import com.example.speedometerview.utils.SpeedAnimatorConst.ANIM_REPEAT_COUNT
import com.example.speedometerview.utils.SpeedAnimatorConst.ANIM_REPEAT_MODE
import com.example.speedometerview.utils.SpeedAnimatorConst.GRADIENT_KEY
import com.example.speedometerview.utils.SpeedAnimatorConst.SCALE_KEY
import com.example.speedometerview.utils.SpeedAnimatorConst.SPEED_KEY
import com.example.speedometerview.utils.SpeedometerAnimatorFunc.getScalePositions
import com.example.speedometerview.utils.SpeedometerAnimatorFunc.getSpeedCounterColors
import com.example.speedometerview.utils.SpeedometerAnimatorFunc.getSpeedPositions


class MainActivity : AppCompatActivity() {

    private lateinit var speedometerView: SpeedometerView
    private lateinit var tvCurrentSpeed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speedometerView = findViewById<SpeedometerView>(R.id.speedometer_view)
        tvCurrentSpeed = findViewById<TextView>(R.id.currentSpeed)

        val speedAnimator = getSpeedAnimator()
        tvCurrentSpeed.setOnClickListener {
            animate(speedAnimator)
        }
    }

    private fun animate(anim: ValueAnimator) {

        anim.run {
            if (!isRunning)
                start()
            else
                if (isPaused) resume() else pause()
        }

    }


    private fun getSpeedAnimator(): ValueAnimator {

        // PropertyValuesHolders
        val gradientHolder = PropertyValuesHolder
            .ofObject(GRADIENT_KEY,ArgbEvaluator(), *getSpeedCounterColors().toTypedArray())
        val speedHolder = PropertyValuesHolder
            .ofFloat(SPEED_KEY, *speedometerView.getSpeedPositions())
        val scaleHolder = PropertyValuesHolder
            .ofFloat(SCALE_KEY, *getScalePositions())

        return ValueAnimator.ofPropertyValuesHolder(gradientHolder, speedHolder, scaleHolder).apply {
            duration = ANIM_DURATION
            repeatMode = ANIM_REPEAT_MODE
            repeatCount = ANIM_REPEAT_COUNT
            addUpdateListener { animation ->
                val color = animation.getAnimatedValue(GRADIENT_KEY) as Int

                val speed = animation.getAnimatedValue(SPEED_KEY) as Float

                val scale = animation.getAnimatedValue(SCALE_KEY) as Float

                speedometerView.setSpeed(speed.toInt())


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