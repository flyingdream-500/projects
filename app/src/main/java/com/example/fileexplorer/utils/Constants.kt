package com.example.fileexplorer.utils

import com.example.fileexplorer.R
import com.example.fileexplorer.utils.FileFormatExtensions.isCompressionFormat
import com.example.fileexplorer.utils.FileFormatExtensions.isDocumentFormat
import com.example.fileexplorer.utils.FileFormatExtensions.isMusicFormat
import com.example.fileexplorer.utils.FileFormatExtensions.isPictureFormat
import com.example.fileexplorer.utils.FileFormatExtensions.isVideoFormat
import java.io.File

object Constants {
    const val ARG_EXTERNAL_PATH = "ARG_EXTERNAL_PATH"
    const val ARG_EXTERNAL_URI = "ARG_EXTERNAL_URI"
    const val FILES_FRAGMENT_TAG = "FILES_FRAGMENT_TAG"
}

object FileConfig {

    fun File.getFileItemsCount(): Int {
        val list = list()
        return list?.size ?: 0
    }

    fun String.getType(): Int {
        return when {
            isCompressionFormat() -> R.drawable.ic_zip
            isMusicFormat() -> R.drawable.ic_music
            isVideoFormat() -> R.drawable.ic_video
            isDocumentFormat() -> R.drawable.ic_doc
            isPictureFormat() -> R.drawable.ic_picture
            else -> R.drawable.ic_file
        }
    }
}

object FileFormatExtensions {

    fun String.isVideoFormat(): Boolean = VIDEO_FORMATS.checkListByFormat(this)

    fun String.isMusicFormat(): Boolean = MUSIC_FORMATS.checkListByFormat(this)

    fun String.isPictureFormat(): Boolean = PICTURE_FORMATS.checkListByFormat(this)

    fun String.isDocumentFormat(): Boolean = DOCUMENT_FORMATS.checkListByFormat(this)

    fun String.isCompressionFormat(): Boolean = COMPRESSION_FORMATS.checkListByFormat(this)


    private fun List<String>.checkListByFormat(path: String) =
        any { format -> path.endsWith(format, true) }


    private val VIDEO_FORMATS = arrayListOf(
        ".webm",
        ".mp4",
        ".avi",
        ".wmv",
        ".3gp",
        ".flv"
    )

    private val MUSIC_FORMATS = arrayListOf(
        ".mp3",
        ".aac"
    )

    private val PICTURE_FORMATS = arrayListOf(
        ".jpeg",
        ".jpg",
        ".png",
        ".webp"
    )

    private val DOCUMENT_FORMATS = arrayListOf(
        ".doc",
        ".docx",
        ".pdf",
        ".txt",
    )


    private val COMPRESSION_FORMATS = arrayListOf(
        ".zip",
        ".7z",
        ".gzip",
    )

}