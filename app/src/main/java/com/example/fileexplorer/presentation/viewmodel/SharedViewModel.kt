package com.example.fileexplorer.presentation.viewmodel

import android.os.Environment
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fileexplorer.data.model.ExternalFileItem
import com.example.fileexplorer.domain.ExternalFilesRepository

class SharedViewModel(
    private val repository: ExternalFilesRepository
): ViewModel() {

    private var BASE_PATH = Environment.getExternalStorageDirectory().absolutePath
    private var stack = ArrayDeque<String>()

    fun removeElement(path: String) {
        if (path != BASE_PATH) {
            stack.remove(path)
        }
    }
    fun addElement(path: String) {
        if (path != BASE_PATH) {
            stack.add(path)
        }
    }

    private val externalPathLiveData = MutableLiveData<String>().apply {
        value = Environment.getExternalStorageDirectory().absolutePath
    }
    fun observeExternalPath() = externalPathLiveData as LiveData<String>

    private val externalDocumentFileLiveData = MutableLiveData<DocumentFile>()
    fun observeExternalDocumentFile() = externalDocumentFileLiveData as LiveData<DocumentFile>


    private val externalItemsLiveData = MutableLiveData<List<ExternalFileItem>>()
    fun observeExternalItems() = externalItemsLiveData as LiveData<List<ExternalFileItem>>

    fun setItems(list: List<ExternalFileItem>) {
        externalItemsLiveData.value = list
    }

    fun setDocumentFile(documentFile: DocumentFile?) {
        externalDocumentFileLiveData.value = documentFile!!
    }
    fun getDocumentFile() = externalDocumentFileLiveData.value

    fun getDocumentFiles(documentFile: DocumentFile) = repository.getDocumentFiles(documentFile)


    fun getPath(): String? = externalPathLiveData.value
    fun changePath(path: String) {
        externalPathLiveData.value = path
    }
    fun getExternalFiles(path: String) = repository.getExternalFiles(path)
}