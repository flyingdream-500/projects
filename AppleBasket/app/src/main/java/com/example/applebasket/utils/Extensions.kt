package com.example.applebasket.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

object Extensions {
    fun RecyclerView.visible() {
        this.visibility = View.VISIBLE
    }

    fun RecyclerView.gone() {
        this.visibility = View.GONE
    }
}