package com.example.finalproject.utils.extensions

import android.text.Editable
import android.widget.EditText
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.finalproject.utils.extensions.BaseExtensions.baseRatedBalance
import com.example.finalproject.utils.extensions.BaseExtensions.targetRatedBalance
import com.example.finalproject.utils.inputfilters.NumbersInputFilter
import java.math.RoundingMode

object EditTextExtensions {

    // Обновление поля ввода базовой валюты
    fun EditText.baseUpdate(
        text: Editable?,
        isFocused: Boolean,
        price: Float,
        roundingMode: RoundingMode
    ) {
        if (isFocused) {
            val input = text.toString().toFloatOrNull()
            if (input != null) {
                val ratedBalance = baseRatedBalance(input, price, roundingMode)
                setText(ratedBalance.toString())
            } else {
                setText("0")
            }
        }
    }

    // Обновление поля ввода целевой валюты
    fun EditText.targetUpdate(
        text: Editable?,
        isFocused: Boolean,
        price: Float,
        roundingMode: RoundingMode
    ) {
        if (isFocused) {
            val input = text.toString().toFloatOrNull()
            if (input != null) {
                val ratedBalance = targetRatedBalance(input, price, roundingMode)
                setText(ratedBalance.toString())
            } else {
                setText("0")
            }
        }
    }

    // Устанавливаем InputFilters для EditText
    fun EditText.initDetailInputFilters(rate: Float, balance: Float) {
        val limitFilter = NumbersInputFilter.MinMaxInputFilter(0f, balance * rate)
        val decimalFilter = NumbersInputFilter.DecimalDigitsInputFilter()
        this.filters = arrayOf(decimalFilter, limitFilter)
    }

    // Размещаем изображение целевого курса валют справа от поля ввода
    fun EditText.rightDrawable(@DrawableRes id: Int = 0, @DimenRes sizeRes: Int) {
        val drawable = ContextCompat.getDrawable(context, id)
        val size = resources.getDimensionPixelSize(sizeRes)
        drawable?.setBounds(0, 0, size, size)
        this.setCompoundDrawables(null, null, drawable, null)
    }
}