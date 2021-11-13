package com.example.voicerecorderapp.utils

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
}