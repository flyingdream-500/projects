package com.example.contentproviderapp.domain.interactor

import com.example.contentproviderapp.domain.model.DictionaryItem
import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IDictionaryInteractor {
    fun add(dictionaryItem: DictionaryItem): Completable
    fun getList(): Single<List<DictionaryItemModel>>
    fun delete(id: Long): Completable
}