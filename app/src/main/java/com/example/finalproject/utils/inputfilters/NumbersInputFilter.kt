package com.example.finalproject.utils.inputfilters

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

object NumbersInputFilter {

    private const val regex = "[0-9]*+((\\.[0-9]?)?)||(\\.)?"
    private val mPattern: Pattern = Pattern.compile(regex)

    /**
     * InputFilter ограничивает до 2 знаков после точки
     */
    class DecimalDigitsInputFilter : InputFilter {

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val matcher: Matcher = mPattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }

    }


    /**
     * InputFilter ограничивает поле ввода до максимальной доступной суммы
     */
    class MinMaxInputFilter(
        private var min: Float,
        private var max: Float
    ) : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input = (dest.toString() + source.toString()).toFloat()
                if (isInRange(min, max, input)) return null
            } catch (nfe: NumberFormatException) {
            }
            return ""
        }

        private fun isInRange(a: Float, b: Float, c: Float): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }
}