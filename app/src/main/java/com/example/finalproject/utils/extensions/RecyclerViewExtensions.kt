package com.example.finalproject.utils.extensions

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewExtensions {

    /**
     * Добавление разделителя списка курса валют
     */
    fun RecyclerView.addVerticalDivider(context: Context) {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        addItemDecoration(divider)
    }


    /**
     * Горизонтальный Linear layout для списка
     */
    fun RecyclerView.addHorizontalLinearLayout(context: Context) {
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}