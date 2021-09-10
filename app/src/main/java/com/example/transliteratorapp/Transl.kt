package com.example.transliteratorapp

import android.text.TextUtils
import java.lang.StringBuilder

fun main() {
    val res = "qw"
    val w = res.substring(0, 10)
    print(w)

}


object Translit {
    const val UPPER = 1
    const val LOWER = 2
    val map = makeTranslitMap()
    private fun makeTranslitMap(): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map["a"] = "а"
        map["b"] = "б"
        map["v"] = "в"
        map["g"] = "г"
        map["d"] = "д"
        map["e"] = "е"
        map["yo"] = "ё"
        map["zh"] = "ж"
        map["z"] = "з"
        map["i"] = "и"
        map["j"] = "й"
        map["k"] = "к"
        map["l"] = "л"
        map["m"] = "м"
        map["n"] = "н"
        map["o"] = "о"
        map["p"] = "п"
        map["r"] = "р"
        map["s"] = "с"
        map["t"] = "т"
        map["u"] = "у"
        map["f"] = "ф"
        map["h"] = "х"
        map["ts"] = "ц"
        map["ch"] = "ч"
        map["sh"] = "ш"
        map["`"] = "ъ"
        map["y"] = "у"
        map["'"] = "ь"
        map["yu"] = "ю"
        map["ya"] = "я"
        map["x"] = "кс"
        map["w"] = "в"
        map["q"] = "к"
        map["iy"] = "ий"
        return map
    }

    private fun charClass(c: Char): Int {
        return if (Character.isUpperCase(c)) UPPER else LOWER
    }

    private operator fun get(s: String): String {
        val charClass = charClass(s[0])
        val result = map[s.toLowerCase()]
        return if (result == null) "" else if (charClass == UPPER) (result[0].toString() + "").toUpperCase() +
                (if (result.length > 1) result.substring(1) else "") else result
    }

    fun translit(text: String): String {
        val len = text.length
        if (len == 0) {
            return text
        }
        if (len == 1) {
            return Translit[text]
        }
        val sb = StringBuilder()
        var i = 0
        while (i < len) {

            // get next 2 symbols
            val toTranslate = text.substring(i, if (i <= len - 2) i + 2 else i + 1)
            // trying to translate
            var translated = Translit[toTranslate]
            // if these 2 symbols are not connected try to translate one by one
            if (TextUtils.isEmpty(translated)) {
                translated = Translit[toTranslate[0].toString() + ""]
                sb.append(if (TextUtils.isEmpty(translated)) toTranslate[0] else translated)
                i++
            } else {
                sb.append(if (TextUtils.isEmpty(translated)) toTranslate else translated)
                i += 2
            }
        }
        return sb.toString()
    }
}