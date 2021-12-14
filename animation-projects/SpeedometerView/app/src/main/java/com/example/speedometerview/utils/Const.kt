package com.example.speedometerview.utils

import android.animation.ValueAnimator

object SpeedometerViewConst {
    const val START_COUNTDOWN_ANGLE = 60f
    const val END_COUNTDOWN_ANGLE = -300f

    const val START_ARROW_ANGLE = 30f
    const val END_ARROW_ANGLE = 300f

    const val ARC_LENGTH = 5 * Math.PI / 3
    const val COUNT_OF_MARKS = 30
    const val STEP_OF_MARKS = ARC_LENGTH / COUNT_OF_MARKS

    const val COUNT_OF_NUMBERS = 11
    const val MARKS_PER_NUMBER = 3

    const val MIN_WRAP = 700
}

object SpeedAnimatorConst {
    const val SPEED_KEY = "speed"
    const val GRADIENT_KEY = "gradient"
    const val SCALE_KEY = "scale"
    const val ANIM_DURATION = 5_000L
    const val ANIM_REPEAT_MODE = ValueAnimator.REVERSE
    const val ANIM_REPEAT_COUNT = 1
}