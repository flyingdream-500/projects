package com.example.transliteratorapp

fun main() {
    val str = "shchavel"
    print(str.length)
}

fun isTwoSymbols(str: String): Boolean {
    return false
}

fun getTransliteratedStr(source: String): String {
    val current = 0
    val chars = source.substring(current, current+1)

    return ""
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
        //
        put("i", "и");
        put("i  ", "й");
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