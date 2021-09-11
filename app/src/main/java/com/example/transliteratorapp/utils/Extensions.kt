package com.example.transliteratorapp.utils

import android.text.InputFilter
import android.view.animation.LinearInterpolator
import android.widget.EditText
import android.widget.ImageView
import com.example.transliteratorapp.utils.Const.LOWER
import com.example.transliteratorapp.utils.Const.UPPER


object Const {
    const val UPPER = 1
    const val LOWER = 2

    const val MAX_LINES = 3
    const val MAX_LETTERS = 100
}

object Extensions {
    /**
        Получить и вставить регистр для первого символа строки
     */
    fun Char.getRegister(): Int = if (this.isUpperCase()) UPPER else LOWER
    fun String.toRegisterCase(charClass: Int) =
        if (charClass == UPPER) this.replace(this[0], this[0].uppercaseChar())
        else this.replace(this[0], this[0].lowercaseChar())

    /**
        Получение первых 2-х и 4-х символов строки соответсвенно
     */
    fun String.getTwoSymbols(i: Int) = this.substring(i, i + 2).lowercase()
    fun String.getFourSymbols(i: Int) = this.substring(i, i + 4).lowercase()

    /**
        Фильтры для EditText:
        * noStartedSpaceFilter -> фильтр не позволяет начинать текст с пробела или нового абзаца
        * lengthFilter -> ограничивает количество символов для вводимого текста
        * maxLinesFilter -> ограничивает количество вводимых линий для EditText
     */
    fun EditText.initInputFilters(maxLetters: Int, maxLines: Int) {
        val noStartedSpaceFilter = InputFilter { source, _, _, _, _, _ ->
            if (source.isNotEmpty() && Character.isWhitespace(source[0]) && this.text.isEmpty()) {
                ""
            } else source
        }
        val lengthFilter = InputFilter.LengthFilter(maxLetters)
        val maxLinesFilter = MaxLinesInputFilter(maxLines)
        this.filters = arrayOf(noStartedSpaceFilter, lengthFilter, maxLinesFilter)
    }

    /**
        Анимация поворота изображения на 180 градусов
     */
    fun ImageView.rotate() {
        this
            .animate()
            .rotationBy(180f)
            .setDuration(300)
            .setInterpolator(LinearInterpolator())
            .start()
    }
}


