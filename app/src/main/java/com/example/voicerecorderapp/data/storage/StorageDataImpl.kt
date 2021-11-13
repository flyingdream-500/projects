package com.example.voicerecorderapp.data.storage

import android.os.Environment
import java.io.File

/**
 * Реализация интерфейса [IStorageData]
 */
class StorageDataImpl : IStorageData {

    //Базовая папка во внутренней памяти смартфона
    private val rootFile = File(Environment.getExternalStorageDirectory(), "VoiceRecords/")

    override fun getRecordFiles(): List<File> {
        createRootDirectory(rootFile)
        val recordFiles = rootFile.listFiles()
        recordFiles?.let { array ->
            return array.toList()
        }
        return emptyList()
    }

    //Проверка существования папки, при отсутствии создаем новую
    private fun createRootDirectory(rootFile: File) {
        if (!rootFile.exists() || !rootFile.isDirectory) {
            rootFile.mkdirs()
        }
    }
}