package com.example.contentproviderapp.domain.repository

import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IDictionaryRepository {

    fun add(dictionaryItemModel: DictionaryItemModel): Completable
    fun getList(): Single<List<DictionaryItemModel>>
    fun delete(id: Long): Completable
}