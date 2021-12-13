package com.example.voicerecorderapp.domain.repository

import com.example.voicerecorderapp.data.model.RecordItem

/**
 * Интерфейс репозитория для получения списка аудиозаписей из корневой папки
 */
interface IRecordsRepository {
    fun getRecords(): List<RecordItem>
}