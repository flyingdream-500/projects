package com.example.transliteratorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.transliteratorapp.utils.Const.MAX_LETTERS
import com.example.transliteratorapp.utils.Const.MAX_LINES
import com.example.transliteratorapp.utils.Extensions.initInputFilters
import com.example.transliteratorapp.utils.Extensions.rotate
import com.example.transliteratorapp.utils.Translit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * Определение первоначального положения стрелки изображения
     */
    private var firstStart: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRusInput()
        initEngInput()
    }

    /**
        Инициализация слушателей:
        - фокуса(для анимации картинки)
        - фильтров для ограничения поведения
        - динамическая обработка текста и транслит
     */
    private fun initEngInput() {
        input_eng.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    firstStart = false
                    iv_direct.rotate()
                }
            }
            initInputFilters(MAX_LETTERS, MAX_LINES)
            doOnTextChanged { text, _, _, _ ->
                if (input_eng.isFocused) {
                    val txt = Translit.engToRus(text.toString())
                    input_rus.setText(txt)
                }
            }
        }
    }

    private fun initRusInput() {
        input_rus.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    firstStart = false
                    iv_direct.rotate()
                }
            }
            initInputFilters(MAX_LETTERS, MAX_LINES)
            doOnTextChanged { text, _, _, _ ->
                if (input_rus.isFocused) {
                    val txt = Translit.rusToEnd(text.toString())
                    input_eng.setText(txt)
                }
            }
        }
    }

}