package com.example.voicerecorderapp.data.converter

import com.example.voicerecorderapp.data.model.RecordItem
import java.io.File

/**
 * Реализация интерфейса [IRecordItemConverter]
 */
class RecordItemConverterImpl : IRecordItemConverter {

    override fun convert(file: File): RecordItem =
        RecordItem(
            name = file.name.recordName(),
            path = file.absolutePath,
            size = file.getSize(),
            isPlaying = false
        )


    /**
     * Убираем из имени файла символы расширения
     */
    private fun String.recordName() = substringBeforeLast(".")

    private fun File.sizeInKb() = length() / 1024
    private fun Long.toKbText() = String.format("%d kB", this)

    private fun File.sizeInMb() = length().toDouble() / 1024 / 1024
    private fun Double.toMbText() = String.format("%.2f mB", this)

    /**
     * Получаем размер файла в текстовом виде,
     *  отображаем размер в КБ или в МБ
     */
    private fun File.getSize() =
        if (sizeInMb() > 1) sizeInMb().toMbText() else sizeInKb().toKbText()
}

