package com.example.transliteratorapp

import com.example.transliteratorapp.Res.toCase
import java.lang.StringBuilder


fun main() {
    val s = "ксяоми"
    val res = Res.cyrToLat(s)
    print(res)
}

object Res {

    const val UPPER = 1
    const val LOWER = 2

    fun Char.isUpper(): Int = if (this.isUpperCase()) UPPER else LOWER

    fun Char.toCase(charClass: Int) = if (charClass == UPPER) this.toUpperCase() else this.toLowerCase()

    fun cyrToLat(s: String): String {
        val length = s.length
        val sb = StringBuilder()
        val map = rusToEng()
        var i = 0
        while (i < length) {
            var char = s[i]
            val register = char.isUpper()
            char = char.toLowerCase()
            when(char) {
                'к' -> if (i + 1 < length) {
                    val k = s.substring(i, i+2).toLowerCase()
                    val res = map.get(k)
                    if (res != null) {
                        sb.append(res)
                        i += 2
                    } else {
                        val res = map.get("к")
                        sb.append(res)
                        i += 1
                    }
                } else {
                    val res = map.get("к")
                    sb.append(res)
                    i += 1
                }
                else -> {
                    val res = map.get(char.toString())
                    if (res != null) {
                        sb.append(res)
                    }
                    i += 1
                }
            }
        }
        return sb.toString()
    }

    fun latToCyr(s: String): String {
        val length = s.length
        val sb = StringBuilder()
        val map = engToRus()
        var i = 0
        while (i < length) {
            var char = s[i]
            val register = char.isUpper()
            char = char.toLowerCase()
            when(char) {
                'i' -> if (i + 1 < length) {
                    val k = s.substring(i, i+2).toLowerCase()
                    val res = map.get(k)
                    if (res != null) {
                        sb.append(res[0].toCase(register))
                        i += 2
                    } else {
                        val res = map.get("i")
                        sb.append(res!![0].toCase(register))
                        i += 1
                    }
                } else {
                    val res = map.get("i")
                    sb.append(res!![0].toCase(register))
                    i += 1
                }
                'z' -> if (i + 1 < length) {
                    val k = s.substring(i, i+2).toLowerCase()
                    val res = map.get(k)
                    if (res != null) {
                        sb.append(res[0].toCase(register))
                        i += 2
                    } else {
                        val res = map.get("z")
                        sb.append(res!![0].toCase(register))
                        i += 1
                    }
                } else {
                    val res = map.get("z")
                    sb.append(res!![0].toCase(register))
                    i += 1
                }
                'k' -> if (i + 1 < length) {
                    val k = s.substring(i, i+2).toLowerCase()
                    val res = map.get(k)
                    if (res != null) {
                        sb.append(res[0].toCase(register))
                        i += 2
                    } else {
                        val res = map.get("k")
                        sb.append(res!![0].toCase(register))
                        i += 1
                    }
                } else {
                    val res = map.get("k")
                    sb.append(res!![0].toCase(register))
                    i += 1
                }
                't' -> if (i + 1 < length) {
                    val k = s.substring(i, i+2).toLowerCase()
                    val res = map.get(k)
                    if (res != null) {
                        sb.append(res[0].toCase(register))
                        i += 2
                    } else {
                        val res = map.get("t")
                        sb.append(res!![0].toCase(register))
                        i += 1
                    }
                } else {
                    val res = map.get("t")
                    sb.append(res!![0].toCase(register))
                    i += 1
                }
                'c' -> if (i + 1 < length) {
                    val k = s.substring(i, i+2).toLowerCase()
                    val res = map.get(k)
                    if (res != null) {
                        sb.append(res[0].toCase(register))
                        i += 2
                    } else {
                        i += 1
                    }
                }  else {
                    i += 1
                }
                's' -> {
                    if (i + 3 < length) {
                        val k = s.substring(i, i+4).toLowerCase()
                        val res = map.get(k)
                        if (res != null) {
                            sb.append(res[0].toCase(register))
                            i += 4
                            continue
                        }
                    }
                    if (i + 1 < length) {
                        val k = s.substring(i, i+2).toLowerCase()
                        val res = map.get(k)
                        if (res != null) {
                            sb.append(res[0].toCase(register))
                            i += 2
                        } else {
                            val res = map.get("s")
                            sb.append(res!![0].toCase(register))
                            i += 1
                        }
                    } else {
                        val res = map.get("s")
                        sb.append(res!![0].toCase(register))
                        i += 1
                    }
                }
                else -> {
                    val res = map.get(char.toString())
                    if (res != null) {
                        sb.append(res[0].toCase(register))
                    }
                    i += 1
                }
            }
        }
        return sb.toString()
    }

    fun doubkeCheck(i: Int,length: Int, sb: StringBuilder, s: String): StringBuilder {
        var i = i
        if (i + 2 < length) {
            val k = s.substring(i, i+2)
            val res = engToRus().get(k)
            if (res != null) {
                sb.append(res)
                i += 2
            } else {
                val res = engToRus().get("i")
                sb.append(res)
                i += 1
            }
        } else {
            val res = engToRus().get("i")
            sb.append(res)
            i += 1
        }
        return sb
    }



    fun rusToEng(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.apply {
            put("а", "a");
            put("б", "b");
            put("в", "v");
            put("г", "g");
            put("д", "d");
            put("е", "e");
            put("ё", "e");
            put("ж", "zh");
            put("з", "z");
            put("и", "i");
            put("й", "i");
            put("к", "k");
            put("л", "l");
            put("м", "m");
            put("н", "n");
            put("о", "о");
            put("п", "p");
            put("р", "r");
            put("с", "s");
            put("т", "t");
            put("у", "u");
            put("ф", "f");
            put("х", "kh");
            put("ц", "ts");
            put("ч", "ch");
            put("ш", "sh");
            put("щ", "shch");
            put("ъ", "ie");
            put("ы", "y");
            put("ь", "`");
            put("э", "e");
            put("ю", "iu");
            put("я", "ia");
            //
            put("кс", "x");
            put(" ", " ")
            put("\n", "\n")
        }
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

            put("l", "л");
            put("m", "м");
            put("n", "н");
            put("o", "о");
            put("p", "п");
            put("r", "р");


            put("u", "у");
            put("f", "ф");
            put("y", "ы");
            put("`", "ь");
            put("e", "э");
            put("x", "кс");
            put("w", "в");
            put("q", "к");
            put(" ", " ")
            put("\n", "\n")
            //
            put("i", "и");
            put("ii", "ий");
            put("ie", "ъ");
            put("iu", "ю");
            put("ia", "я");
            //
            put("z", "з");
            put("zh", "ж");
            //
            put("k", "к");
            put("kh", "х");
            //
            put("t", "т");
            put("ts", "ц");
            //
            put("ch", "ч");
            //
            put("s", "с");
            put("sh", "ш");
            put("shch", "щ");
        }
        return map
    }
}