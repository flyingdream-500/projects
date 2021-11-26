package com.example.finalproject.utils.extensions

import android.widget.TextView
import com.example.finalproject.R

object TextViewExtensions {

    /**
     * Отображение сообщения ошибки, или сообщения о неизвестной ошибке
     */
    fun TextView.setErrorMessage(message: String?) {
        text = message ?: resources.getString(R.string.unknown_error)
    }

}