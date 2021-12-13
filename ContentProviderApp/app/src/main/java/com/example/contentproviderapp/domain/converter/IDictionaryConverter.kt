package com.example.contentproviderapp.domain.converter

import com.example.contentproviderapp.domain.model.DictionaryItem
import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel


/**
 * Converter interface for model classes [DictionaryItem] and [DictionaryItemModel]
 */
interface IDictionaryConverter {

    fun convert(dictionaryItem: DictionaryItem): DictionaryItemModel
    fun reverse(dictionaryItemModel: DictionaryItemModel): DictionaryItem
    fun reverseList(dictionaryItemModels: List<DictionaryItemModel>): List<DictionaryItem>

}