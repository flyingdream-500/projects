package com.example.voicerecorderapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс-модель аудиозаписи из директории
 */
@Parcelize
data class RecordItem(
    val name: String,
    val path: String,
    val size: String,
    var isPlaying: Boolean = false
) : Parcelable