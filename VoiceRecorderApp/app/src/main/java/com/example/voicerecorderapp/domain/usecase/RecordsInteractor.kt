package com.example.voicerecorderapp.domain.usecase

import com.example.voicerecorderapp.data.model.RecordItem
import com.example.voicerecorderapp.domain.repository.IRecordsRepository

/**
 * Интерактор для получения аудиозаписей из хранилища
 */
class RecordsInteractor(
    private val recordsRepository: IRecordsRepository
) {
    fun getRecords(): List<RecordItem> {
        return recordsRepository.getRecords()
    }
}