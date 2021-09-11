package com.example.transliteratorapp.utils

import android.text.Spanned

import android.text.InputFilter


/**
 * Filter for controlling maximum new lines in EditText.
 */
class MaxLinesInputFilter(
    private val max: Int
) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val newLinesToBeAdded = countOccurrences(source.toString(), '\n')
        val newLinesBefore = countOccurrences(dest.toString(), '\n')
        return if (newLinesBefore >= max - 1 && newLinesToBeAdded > 0) {
            // filter
            ""
        } else null

        // do nothing
    }

    companion object {
        fun countOccurrences(string: String, charAppearance: Char): Int {
            var count = 0
            for (i in 0 until string.length) {
                if (string[i] == charAppearance) {
                    count++
                }
            }
            return count
        }
    }
}