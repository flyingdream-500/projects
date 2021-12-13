package com.example.contentproviderapp.domain.interactor

import com.example.contentproviderapp.domain.repository.ILocalImageRepository

class LocalImageInteractorImpl(
    private val localImageRepository: ILocalImageRepository
) : ILocalImageInteractor {
    override fun getLocalImages() =
        localImageRepository.getLocalImages()

}