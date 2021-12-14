package com.example.fileexplorer.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.File

@RequiresApi(Build.VERSION_CODES.Q)
fun Context.hasPermissionToAccessSdCard(pathFile: File): Boolean {
    val volume = getVolume(pathFile.absolutePath) ?: return false
    val persistedUriPermissions = contentResolver.persistedUriPermissions
    persistedUriPermissions.forEach {
        val documentId = it.uri.getTreeDocumentId()
        val set = documentId?.split(":")
        if (volume == MediaStore.VOLUME_EXTERNAL_PRIMARY && set?.first() == "primary"
            || set?.first().equals(volume, true)
        ) {
            return true
        }
    }

    return false
}

@RequiresApi(Build.VERSION_CODES.N)
fun Uri.getTreeDocumentId(): String? {
    if (DocumentsContract.isTreeUri(this)) {
        return DocumentsContract.getTreeDocumentId(this)
    }
    return null
}

@RequiresApi(Build.VERSION_CODES.Q)
fun Context.getVolume(uri: String): String? {
    val pathSet = uri.split("/")
    if (pathSet.isEmpty()) return null

    val directoryName = pathSet.last()
    if (directoryName.isEmpty()) return null

    val volumes = MediaStore.getExternalVolumeNames(this)
    volumes.forEach { if (it.equals(directoryName, true)) return directoryName }
    if (volumes.contains(MediaStore.VOLUME_EXTERNAL_PRIMARY)) return MediaStore.VOLUME_EXTERNAL_PRIMARY
    return null
}