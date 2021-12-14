package com.example.fileexplorer.domain

import androidx.documentfile.provider.DocumentFile
import com.example.fileexplorer.data.model.ExternalFileItem

interface ExternalFilesRepository {
    fun getDocumentFiles(documentFile: DocumentFile): List<ExternalFileItem>
    fun getExternalFiles(path: String): List<ExternalFileItem>
}