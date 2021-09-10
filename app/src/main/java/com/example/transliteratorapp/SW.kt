package com.example.transliteratorapp

import java.lang.Character.toUpperCase
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

object Translits {
    fun lat2cyr(s: String): String {
        val sb = StringBuilder(s.length)
        var i = 0
        while (i < s.length) { // Идем по строке слева направо. В принципе, подходит для обработки потока
            var ch = s[i]
            val lc = Character.isLowerCase(ch) // для сохранения регистра
            ch = toUpperCase(ch)
            if (ch == 'J') { // Префиксная нотация вначале
                i++ // преходим ко второму символу сочетания
                ch = toUpperCase(s[i])
                when (ch) {
                    'O' -> sb.append(Translits.ch('Ё', lc))
                    'H' -> if (i + 1 < s.length && toUpperCase(s[i + 1]) === 'H') { // проверка на постфикс (вариант JHH)
                        sb.append(Translits.ch('Ъ', lc))
                        i++ // пропускаем постфикс
                    } else {
                        sb.append(Translits.ch('Ь', lc))
                    }
                    'U' -> sb.append(Translits.ch('Ю', lc))
                    'A' -> sb.append(Translits.ch('Я', lc))
                    else -> throw IllegalArgumentException("Illegal transliterated symbol '$ch' at position $i")
                }
            } else if (i + 1 < s.length && toUpperCase(s[i + 1]) === 'H') { // Постфиксная нотация, требует информации о двух следующих символах. Для потока придется сделать обертку с очередью из трех символов.
                when (ch) {
                    'Z' -> sb.append(Translits.ch('Ж', lc))
                    'K' -> sb.append(Translits.ch('Х', lc))
                    'C' -> sb.append(Translits.ch('Ч', lc))
                    'S' -> if (i + 2 < s.length && toUpperCase(s[i + 2]) === 'H') { // проверка на двойной постфикс
                        sb.append(Translits.ch('Щ', lc))
                        i++ // пропускаем первый постфикс
                    } else {
                        sb.append(Translits.ch('Ш', lc))
                    }
                    'E' -> sb.append(Translits.ch('Э', lc))
                    'I' -> sb.append(Translits.ch('Ы', lc))
                    else -> throw IllegalArgumentException("Illegal transliterated symbol '$ch' at position $i")
                }
                i++ // пропускаем постфикс
            } else { // одиночные символы
                when (ch) {
                    'A' -> sb.append(Translits.ch('А', lc))
                    'B' -> sb.append(Translits.ch('Б', lc))
                    'V' -> sb.append(Translits.ch('В', lc))
                    'G' -> sb.append(Translits.ch('Г', lc))
                    'D' -> sb.append(Translits.ch('Д', lc))
                    'E' -> sb.append(Translits.ch('Е', lc))
                    'Z' -> sb.append(Translits.ch('З', lc))
                    'I' -> sb.append(Translits.ch('И', lc))
                    'Y' -> sb.append(Translits.ch('Й', lc))
                    'K' -> sb.append(Translits.ch('К', lc))
                    'L' -> sb.append(Translits.ch('Л', lc))
                    'M' -> sb.append(Translits.ch('М', lc))
                    'N' -> sb.append(Translits.ch('Н', lc))
                    'O' -> sb.append(Translits.ch('О', lc))
                    'P' -> sb.append(Translits.ch('П', lc))
                    'R' -> sb.append(Translits.ch('Р', lc))
                    'S' -> sb.append(Translits.ch('С', lc))
                    'T' -> sb.append(Translits.ch('Т', lc))
                    'U' -> sb.append(Translits.ch('У', lc))
                    'F' -> sb.append(Translits.ch('Ф', lc))
                    'C' -> sb.append(Translits.ch('Ц', lc))
                    else -> sb.append(Translits.ch(ch, lc))
                }
            }
            i++ // переходим к следующему символу
        }
        return sb.toString()
    }

    fun cyr2lat(ch: Char): String {
        return when (ch) {
            'А' -> "A"
            'Б' -> "B"
            'В' -> "V"
            'Г' -> "G"
            'Д' -> "D"
            'Е' -> "E"
            'Ё' -> "E"
            'Ж' -> "ZH"
            'З' -> "Z"
            'И' -> "I"
            'Й' -> "I"
            'К' -> "K"
            'Л' -> "L"
            'М' -> "M"
            'Н' -> "N"
            'О' -> "O"
            'П' -> "P"
            'Р' -> "R"
            'С' -> "S"
            'Т' -> "T"
            'У' -> "U"
            'Ф' -> "F"
            'Х' -> "KH"
            'Ц' -> "TS"
            'Ч' -> "CH"
            'Ш' -> "SH"
            'Щ' -> "SHCH"
            'Ъ' -> "IE"
            'Ы' -> "Y"
            'Ь' -> "`"
            'Э' -> "E"
            'Ю' -> "JU"
            'Я' -> "JA"
            else -> ch.toString()
        }
    }

    fun cyr2lat(s: String): String {
        val sb = StringBuilder(s.length * 2)
        for (ch in s.toCharArray()) {
            val upCh: Char = toUpperCase(ch)
            var lat: String = Translits.cyr2lat(upCh)
            if (ch != upCh) {
                lat = lat.toLowerCase()
            }
            sb.append(lat)
        }
        return sb.toString()
    }

    /**
     * Вспомогательная функция для восстановления регистра
     */
    private fun ch(ch: Char, toLowerCase: Boolean): Char {
        return if (toLowerCase) Character.toLowerCase(ch) else ch
    }

    /**
     * Пробы
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val s1: String = Translits.cyr2lat("Александр Иванович Лебедь")
        val s2: String = Translits.cyr2lat("Веснушчатый Щавелевый")
        val s3: String =
            Translits.cyr2lat("Широкая электрификация южных губерний даст мощный толчок подъёму сельского хозяйства")
        val s4: String =
            Translits.cyr2lat("Съешь же ещё этих мягких французских булок да выпей чаю.")
        val s5: String =
            Translits.cyr2lat("А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я")
        println(s1)
        println(s2)
        println(s3)
        println(s4)
        println(s5)
        println()
        println(Translits.lat2cyr(s1))
        println(Translits.lat2cyr(s2))
        println(Translits.lat2cyr(s3))
        println(Translits.lat2cyr(s4))
        println(Translits.lat2cyr(s5))
    }
}