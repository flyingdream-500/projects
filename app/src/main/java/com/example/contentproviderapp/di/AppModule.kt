package com.example.contentproviderapp.di

import android.app.Application
import android.content.ContentResolver
import com.example.contentproviderapp.data.datastore.db.DataBaseOpenHelper
import com.example.contentproviderapp.data.datastore.provider.DictionaryContentProvider
import com.example.contentproviderapp.data.repository.DictionaryRepositoryImpl
import com.example.contentproviderapp.data.repository.LocalImageRepositoryImpl
import com.example.contentproviderapp.domain.converter.DictionaryConverterImpl
import com.example.contentproviderapp.domain.converter.IDictionaryConverter
import com.example.contentproviderapp.domain.interactor.DictionaryInteractorImpl
import com.example.contentproviderapp.domain.interactor.IDictionaryInteractor
import com.example.contentproviderapp.domain.interactor.ILocalImageInteractor
import com.example.contentproviderapp.domain.interactor.LocalImageInteractorImpl
import com.example.contentproviderapp.domain.repository.IDictionaryRepository
import com.example.contentproviderapp.domain.repository.ILocalImageRepository
import com.example.contentproviderapp.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppBindModule {

    @Binds
    abstract fun bindDictionaryRepositoryImpl_to_IDictionaryRepository(dictionaryRepositoryImpl: DictionaryRepositoryImpl): IDictionaryRepository

    @Binds
    abstract fun bindDictionaryInteractorImpl_to_IDictionaryInteractor(dictionaryInteractorImpl: DictionaryInteractorImpl): IDictionaryInteractor

    @Binds
    abstract fun bindDictionaryConverterImpl_to_IDictionaryConverter(dictionaryConverterImpl: DictionaryConverterImpl): IDictionaryConverter

    @Binds
    abstract fun bindLocalImageRepositoryImpl_to_ILocalImageRepository(localImageRepository: LocalImageRepositoryImpl): ILocalImageRepository

    @Binds
    abstract fun bindLocalImageInteractiorImpl_to_ILocalImageInteractor(localImageInteractorImplImpl: LocalImageInteractorImpl): ILocalImageInteractor

}

@Module
class AppModule {

    @Provides
    fun provideContentResolver(application: Application) = application.contentResolver

    @Provides
    fun provideDataBaseOpenHelper(application: Application) = DataBaseOpenHelper(application.applicationContext)

    /*@Provides
    fun provideDictionaryContentProvider(dataBaseOpenHelper: DataBaseOpenHelper) = DictionaryContentProvider(dataBaseOpenHelper)*/

}

@Module(includes = [AppBindModule::class, AppModule::class])
class DomainModule {

    @Provides
    fun provideDictionaryConverter() = DictionaryConverterImpl()

    @Provides
    fun provideDictionaryRepository(contentResolver: ContentResolver) = DictionaryRepositoryImpl(contentResolver)

    @Provides
    fun provideDictionaryInteractor(
        repository: IDictionaryRepository,
        converter: IDictionaryConverter
    ) =
        DictionaryInteractorImpl(repository, converter)

    @Provides
    fun provideLocalImageRepository(contentResolver: ContentResolver) = LocalImageRepositoryImpl(contentResolver)

    @Provides
    fun provideLocalImageInteractor(localImageRepository: ILocalImageRepository) = LocalImageInteractorImpl(localImageRepository)

}


@Module(includes = [DomainModule::class])
class ViewModelFactoryModule {

    @Provides
    fun provideViewModelFactory(
        application: Application,
        dictionaryInteractor: IDictionaryInteractor,
        localImageInteractor: ILocalImageInteractor
    ): ViewModelFactory {
        return ViewModelFactory(application, dictionaryInteractor, localImageInteractor)
    }
}