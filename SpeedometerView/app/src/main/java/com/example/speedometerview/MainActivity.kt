package com.example.speedometerview

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.speedometerview.utils.Func


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val speedometerView = findViewById<SpeedometerView>(R.id.speedometer_view)
        val tvCurrentSpeed = findViewById<TextView>(R.id.currentSpeed)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        val maxSpeed = speedometerView.getMaxSpeed()
        val currentSpeed = speedometerView.getCurrentSpeed()
        val coefficient = maxSpeed / 100

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                val speed = coefficient * progress

                speedometerView.setSpeed(speed)

                val color = Func.getColorBySpeed(speed, maxSpeed)
                val speedText = String.format(resources.getString(R.string.current_speed), speed)
                val speedSpan = SpannableString(speedText)
                speedSpan.setSpan(
                    ForegroundColorSpan(color),
                    speedText.indexOf(":") + 1,
                    speedText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tvCurrentSpeed.text = speedSpan
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        // заглушка для отображения textView при текущей начальной скорости равной 0
        if (currentSpeed == 0) seekBar.progress = 10 * 100 / maxSpeed

        seekBar.progress = currentSpeed * 100 / maxSpeed
    }


}