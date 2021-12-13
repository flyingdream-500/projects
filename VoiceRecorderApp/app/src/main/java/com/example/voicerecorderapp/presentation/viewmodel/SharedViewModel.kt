package com.example.voicerecorderapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.voicerecorderapp.data.model.RecordItem
import com.example.voicerecorderapp.domain.usecase.RecordsInteractor

/**
 * Shared View Model
 * @param recordsInteractor - интерактор для получения записей
 */
class SharedViewModel(
    private val recordsInteractor: RecordsInteractor
) : ViewModel() {

    private val _recordsLiveData = MutableLiveData<List<RecordItem>>()
    val recordsLiveData: LiveData<List<RecordItem>>
        get() = _recordsLiveData


    fun getRecordItems()  {
        val records = recordsInteractor.getRecords()
        _recordsLiveData.value = records
    }

    fun setRecordItems(updatedItems: List<RecordItem>) {
        _recordsLiveData.value = updatedItems
    }


}