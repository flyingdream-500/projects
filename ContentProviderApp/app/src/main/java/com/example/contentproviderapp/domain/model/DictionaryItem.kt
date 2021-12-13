package com.example.contentproviderapp.domain.model

import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel

/**
 * Model class
 */
data class DictionaryItem(
    val keyword: String,
    val translation: String,
    val logo: String
)