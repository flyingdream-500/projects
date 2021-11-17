package com.example.contentproviderapp.domain.interactor

import com.example.contentproviderapp.domain.converter.IDictionaryConverter
import com.example.contentproviderapp.domain.model.DictionaryItem
import com.example.contentproviderapp.domain.repository.IDictionaryRepository
import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class DictionaryInteractorImpl(
    private val repository: IDictionaryRepository,
    private val converter: IDictionaryConverter
) : IDictionaryInteractor {

    override fun add(dictionaryItem: DictionaryItem): Completable {
        return repository.add(converter.convert(dictionaryItem))
    }

    override fun getList(): Single<List<DictionaryItemModel>> {
        return repository.getList()
    }

    override fun delete(id: Long): Completable {
        return repository.delete(id)
    }
}