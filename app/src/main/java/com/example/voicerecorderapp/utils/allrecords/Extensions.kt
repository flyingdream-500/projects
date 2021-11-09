package com.example.voicerecorderapp.utils.allrecords

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.io.File

object Extensions {
    // Добавление разделителя списка
    fun RecyclerView.addVerticalDivider(context: Context) {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        addItemDecoration(divider)
    }

    fun File.sizeInKb() = length() / 1024
}