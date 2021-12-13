package com.example.contentproviderapp.utils

import android.text.InputFilter
import android.widget.EditText
import androidx.lifecycle.MutableLiveData

const val MAX_LETTERS = 50


/**
 * Set input filters for Edit text
 * @see startingWithoutSpaceFilter - don`t start edit text with space
 * @see lengthFilter - max letters for edit text
 */
fun EditText.setInputFilters() {
    val startingWithoutSpaceFilter = InputFilter { source, _, _, _, _, _ ->
        if (source.isNotEmpty() && Character.isWhitespace(source[0]) && this.text.isEmpty()) {
            ""
        } else source
    }
    val lengthFilter = InputFilter.LengthFilter(MAX_LETTERS)
    filters = arrayOf(startingWithoutSpaceFilter, lengthFilter)
}
