package com.example.transliteratorapp

import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.transliteratorapp.utils.MaxLinesInputFilter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private var firstStart: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val src = "shch"
        val ss = Translits.lat2cyr(src)
        Log.d("TAGG", ss)

        input_name.setOnFocusChangeListener { _, b ->
            if (b) {
                firstStart = false
                rotateAnimation()
            }
        }

        input_surname.setOnFocusChangeListener { _, b ->
            if (b && !firstStart) {
                rotateAnimation()
            }
        }

        input_name.initInputFilters(100, 3)
        input_surname.initInputFilters(100, 3)

        //exec()
    }

    companion object {
        const val UPPER = 1
        const val LOWER = 2
    }
    fun Char.isUpper(): Int = if (this.isUpperCase()) UPPER else LOWER

    fun String.trans(map: Map<String, String>): String {
        val chrClass = this.get(0).isUpper()
        val result = map.get(this.toLowerCase())
        return if (result == null) "" else if (chrClass == UPPER) (result[0].toString() + "").toUpperCase() +
                (if (result.length > 1) result.substring(1) else "") else result
    }

    fun translit(text: String): String {
        val length = text.length
        if (length == 0) return text
        if (length == 1) return text.trans(rusToEng())
        val sb = StringBuilder()

        var i = 0
        while (i < length) {
            // get next 2 symbols
            val toTranslate = text.substring(i, if (i <= length - 2) i + 2 else i + 1)
            // trying to translate
            var translated = toTranslate.trans(engToRus())
            // if these 2 symbols are not connected try to translate one by one
            if (TextUtils.isEmpty(translated)) {
                translated = toTranslate[0].toString() + "".trans(engToRus())
                sb.append(if (TextUtils.isEmpty(translated)) toTranslate[0] else translated)
                i++
            } else {
                sb.append(if (TextUtils.isEmpty(translated)) toTranslate else translated)
                i += 2
            }
        }
        return sb.toString()
    }

    fun checkLength(start: Int, i: Int, length: Int) {

    }



    fun exec() {

        val source = "щавель"
        val engToRus = engToRus()
        val rusToEng = rusToEng()

        val length = source.length
        var start = 0
        var current = 0
        val sb = StringBuilder()

        while (start < length) {
            var cc = 1
            //getIndex
            for(i in 4 downTo 1) {
                if (start + i < length){
                    cc = i
                    break
                }
                if (start + i >= length && i == 1)
                    start += i
                    return
            }
            Log.d("TAGG", "subs: $cc")
            current = start + cc
            while (start < length) {
                val subs1 = source.substring(start, current)
                Log.d("TAGG", "subs: $subs1")
                val a = getChar(subs1, rusToEng)
                if (a != null) {
                    start = current
                    sb.append(a)
                    break
                }
                current--
                if (start <= current) {
                    start += 1
                    break
                }
            }
            Log.d("TAGG", sb.toString())

        }


        Log.d("TAGG", "Itog: ${sb}")


    }

    fun getChar(str: String, map: Map<String,String>): String? {
        return map.get(str)
    }



     fun rusToEng(): Map<String, String> {
        val map = HashMap<String, String>()
        engToRus().forEach { (key, value) -> map.put(value, key) }
        return map
    }

     fun engToRus(): Map<String, String> {
        val map = HashMap<String, String>()
        map.apply {
            put("a", "а");
            put("b", "б");
            put("v", "в");
            put("g", "г");
            put("d", "д");
            put("e", "е");
            put("e", "ё");
            put("zh", "ж");
            put("z", "з");
            put("i", "и");
            put("i ", "й");
            put("k", "к");
            put("l", "л");
            put("m", "м");
            put("n", "н");
            put("o", "о");
            put("p", "п");
            put("r", "р");
            put("s", "с");
            put("t", "т");
            put("u", "у");
            put("f", "ф");
            put("kh", "х");
            put("ts", "ц");
            put("ch", "ч");
            put("sh", "ш");
            put("shch", "щ");
            put("y", "ы");
            put("`", "ь");
            put("ie", "ъ");
            put("e", "э");
            put("iu", "ю");
            put("ia", "я");
            put("x", "кс");
            put("w", "в");
            put("q", "к");
        }
        return map
    }

    private fun EditText.initInputFilters(maxLetters: Int, maxLines: Int) {
        val noStartedSpaceFilter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source.isNotEmpty() && Character.isWhitespace(source[0]) && input_name.text.toString().isEmpty()) {
                ""
            } else source
        }
        val lengthFilter = InputFilter.LengthFilter(maxLetters)
        val maxLinesFilter = MaxLinesInputFilter(maxLines)
        this.filters = arrayOf(noStartedSpaceFilter, lengthFilter, maxLinesFilter)
    }

    private fun rotateAnimation() {
        iv_direct
            .animate()
            .rotationBy(180f)
            .setDuration(300)
            .setInterpolator(LinearInterpolator())
            .start()
    }


}