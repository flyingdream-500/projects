package com.example.transliteratorapp.utils

import com.example.transliteratorapp.utils.Extensions.getFourSymbols
import com.example.transliteratorapp.utils.Extensions.getRegister
import com.example.transliteratorapp.utils.Extensions.getTwoSymbols
import com.example.transliteratorapp.utils.Extensions.toRegisterCase


/**
    Мапы для транслита
 */
object Source {

    fun rusToEngMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.apply {
            put("а", "a")
            put("б", "b")
            put("в", "v")
            put("г", "g")
            put("д", "d")
            put("е", "e")
            put("ё", "e")
            put("ж", "zh")
            put("з", "z")
            put("и", "i")
            put("й", "i")
            put("к", "k")
            put("л", "l")
            put("м", "m")
            put("н", "n")
            put("о", "o")
            put("п", "p")
            put("р", "r")
            put("с", "s")
            put("т", "t")
            put("у", "u")
            put("ф", "f")
            put("х", "kh")
            put("ц", "ts")
            put("ч", "ch")
            put("ш", "sh")
            put("щ", "shch")
            put("ъ", "ie")
            put("ы", "y")
            put("ь", "`")
            put("э", "e")
            put("ю", "iu")
            put("я", "ia")
            // x
            put("кс", "x")
            // others
            put(" ", " ")
            put("\n", "\n")
        }
        return map
    }

    fun engToRusMap(): Map<String, String> {
        val map = HashMap<String, String>()
        map.apply {
            put("a", "а")
            put("b", "б")
            put("v", "в")
            put("g", "г")
            put("d", "д")
            put("l", "л")
            put("m", "м")
            put("n", "н")
            put("o", "о")
            put("p", "п")
            put("r", "р")
            put("u", "у")
            put("f", "ф")
            put("y", "ы")
            put("`", "ь")
            put("x", "кс")
            put("w", "в")
            put("q", "к")
            // e
            put("e", "э")
            put("e", "е")
            // i
            put("i", "и")
            put("ii", "ий")
            put("ie", "ъ")
            put("iu", "ю")
            put("ia", "я")
            // z
            put("z", "з")
            put("zh", "ж")
            // k
            put("k", "к")
            put("kh", "х")
            // t
            put("t", "т")
            put("ts", "ц")
            // c
            put("ch", "ч")
            // s
            put("s", "с")
            put("sh", "ш")
            put("shch", "щ")
            // others
            put(" ", " ")
            put("\n", "\n")
        }
        return map
    }
}

/**
    Функции для транслита
 */
object Translit {

    fun rusToEnd(src: String): String {
        val length = src.length
        val sb = StringBuilder()
        val map = Source.rusToEngMap()
        var i = 0

        //Лямбды для получения буквы
        val singleSymbol = { key: String, register: Int ->
            val symbol = map.get(key)
            if (symbol != null) {
                sb.append(symbol.toRegisterCase(register))
            }
            i += 1
        }
        val doubleSymbol = { key: String, register: Int ->
            if (i + 1 < length) {
                val part = map.get(src.getTwoSymbols(i))
                if (part != null) {
                    sb.append(part.toRegisterCase(register))
                    i += 2
                } else {
                    val symbol = map.get(key)
                    sb.append(symbol!!.toRegisterCase(register))
                    i += 1
                }
            } else {
                val symbol = map.get(key)
                sb.append( symbol!!.toRegisterCase(register))
                i += 1
            }
        }

        while (i < length) {
            var currChar = src[i]
            val register = currChar.getRegister()
            currChar = currChar.lowercaseChar()

            when(currChar) {
                'к' -> doubleSymbol(currChar.toString(), register)
                else -> singleSymbol(currChar.toString(), register)
            }
        }
        return sb.toString()
    }

    fun engToRus(src: String): String {
        val length = src.length
        val sb = StringBuilder()
        val map = Source.engToRusMap()
        var i = 0

        //Лямбды для получения буквы
        val singleSymbol = { key: String, register: Int ->
            val symbol = map.get(key)
            if (symbol != null) {
                sb.append(symbol.toRegisterCase(register))
            }
            i += 1
        }
        val doubleSymbol = { key: String, register: Int ->
            if (i + 1 < length) {
                val part = map.get(src.getTwoSymbols(i))
                if (part != null) {
                    sb.append(part.toRegisterCase(register))
                    i += 2
                } else {
                    val symbol = map.get(key)
                    sb.append(symbol!!.toRegisterCase(register))
                    i += 1
                }
            } else {
                val symbol = map.get(key)
                sb.append( symbol!!.toRegisterCase(register))
                i += 1
            }
        }
        val cSymbol = { key: String, register: Int ->
            if (i + 1 < length) {
                val part = map.get(src.getTwoSymbols(i))
                if (part != null) {
                    sb.append( part.toRegisterCase(register))
                    i += 2
                } else {
                    i += 1
                }
            }  else {
                i += 1
            }
        }


        while (i < length) {
            var currChar = src[i]
            val register = currChar.getRegister()
            currChar = currChar.lowercaseChar()

            when(currChar) {
                'i' -> doubleSymbol(currChar.toString(), register)
                'z' -> doubleSymbol(currChar.toString(), register)
                'k' -> doubleSymbol(currChar.toString(), register)
                't' -> doubleSymbol(currChar.toString(), register)
                'c' -> cSymbol(currChar.toString(), register)
                's' -> {
                    if (i + 3 < length) {
                        val part = map.get(src.getFourSymbols(i))
                        if (part != null) {
                            sb.append( part.toRegisterCase(register))
                            i += 4
                            continue
                        }
                    }
                    doubleSymbol(currChar.toString(), register)
                }
                else -> singleSymbol(currChar.toString(), register)
            }
        }
        return sb.toString()
    }
}