package com.example.voicerecorderapp.data.repository

import com.example.voicerecorderapp.data.model.RecordItem
import com.example.voicerecorderapp.data.storage.IStorageData
import com.example.voicerecorderapp.data.converter.IRecordItemConverter
import com.example.voicerecorderapp.domain.repository.IRecordsRepository

/**
 * Реализация интерфейса [IRecordsRepository]
 * @param recordItemConverter - конвертер файла к классу-модели
 * @param storageData - класс для получения файлов из хранилища
 */
class RecordsRepositoryImpl(
    private val recordItemConverter: IRecordItemConverter,
    private val storageData: IStorageData
) : IRecordsRepository {

    override fun getRecords(): List<RecordItem> {
        val recordFiles = storageData.getRecordFiles()
        recordFiles.let { files ->
            return files.map {
                recordItemConverter.convert(it)
            }
        }
    }
}