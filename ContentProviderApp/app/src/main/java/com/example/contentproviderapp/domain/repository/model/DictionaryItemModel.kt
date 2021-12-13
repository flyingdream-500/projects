package com.example.contentproviderapp.domain.repository.model

/**
 * Model class
 */
data class DictionaryItemModel(
    val id: Long = 0,
    val keyword: String,
    val translation: String,
    val logo: String
)
