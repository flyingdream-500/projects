package com.example.voicerecorderapp.data.storage

import java.io.File

/**
 * Интерфейс для получения списка аудиозаписей из корневой папки
 */
interface IStorageData {
    fun getRecordFiles(): List<File>
}