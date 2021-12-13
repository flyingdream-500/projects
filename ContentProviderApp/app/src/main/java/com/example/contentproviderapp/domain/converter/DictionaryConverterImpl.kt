package com.example.contentproviderapp.domain.converter

import com.example.contentproviderapp.domain.model.DictionaryItem
import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel

/**
 * Converter for model classes [DictionaryItem] and [DictionaryItemModel]
 */
class DictionaryConverterImpl : IDictionaryConverter {

    override fun convert(dictionaryItem: DictionaryItem) =
        DictionaryItemModel(
            keyword = dictionaryItem.keyword,
            translation = dictionaryItem.translation,
            logo = dictionaryItem.logo
        )


    override fun reverse(dictionaryItemModel: DictionaryItemModel) =
        DictionaryItem(
            keyword = dictionaryItemModel.keyword,
            translation = dictionaryItemModel.translation,
            logo = dictionaryItemModel.logo
        )

    override fun reverseList(dictionaryItemModels: List<DictionaryItemModel>) =
        dictionaryItemModels.map { reverse(it) }.toList()

}