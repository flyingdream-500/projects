package com.example.fileexplorer.data.model

import android.net.Uri

data class ExternalFileItem(
    val name: String,
    val filesCount: Int,
    val isDirectory: Boolean,
    val path: String? = null,
    val uri: Uri? = null,
)