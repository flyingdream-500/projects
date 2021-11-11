package com.example.voicerecorderapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordItem(
    val name: String,
    val path: String,
    val size: Long,
    var isPlaying: Boolean = false
) : Parcelable