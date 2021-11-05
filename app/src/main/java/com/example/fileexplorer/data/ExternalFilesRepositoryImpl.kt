package com.example.fileexplorer.data

import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.example.fileexplorer.data.model.ExternalFileItem
import com.example.fileexplorer.domain.ExternalFilesRepository
import com.example.fileexplorer.utils.FileConfig.getFileItemsCount
import java.io.File

class ExternalFilesRepositoryImpl: ExternalFilesRepository {

    override fun getDocumentFiles(documentFile: DocumentFile): List<ExternalFileItem> {
        val listFiles = documentFile.listFiles()
        listFiles.let {
            val items = it.map { documentFile ->
                ExternalFileItem(
                    documentFile.name ?: "Unknown Name",
                    documentFile.listFiles().size,
                    documentFile.isDirectory,
                    uri = documentFile.uri,
                )
            }.toList()
            return items
        }
    }


    override fun getExternalFiles(path: String): List<ExternalFileItem> {
        val file = File(path)
        val listFiles = file.listFiles()

        listFiles?.let {
            Log.d("TAGG", "${listFiles.size}")
            val items = it.map { file ->
                ExternalFileItem(file.name,
                    file.getFileItemsCount(),
                    file.isDirectory,
                    file.path
                )
            }.toList()
            return items
        }
        return emptyList()
    }
}