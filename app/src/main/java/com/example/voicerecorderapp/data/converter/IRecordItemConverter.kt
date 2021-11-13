package com.example.voicerecorderapp.data.converter

import com.example.voicerecorderapp.data.model.RecordItem
import java.io.File

/**
 * Интерфейс для конвертирования файла из директории в класс [RecordItem]
 */
interface IRecordItemConverter {
    fun convert(file: File): RecordItem
}