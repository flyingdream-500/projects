package com.example.voicerecorderapp.di

import android.app.Application
import com.example.voicerecorderapp.data.converter.IRecordItemConverter
import com.example.voicerecorderapp.data.converter.RecordItemConverterImpl
import com.example.voicerecorderapp.data.repository.RecordsRepositoryImpl
import com.example.voicerecorderapp.data.storage.IStorageData
import com.example.voicerecorderapp.data.storage.StorageDataImpl
import com.example.voicerecorderapp.domain.repository.IRecordsRepository
import com.example.voicerecorderapp.domain.usecase.RecordsInteractor
import com.example.voicerecorderapp.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides


/**
 * Корневой модуль
 */
@Module(includes = [ViewModuleFactoryModule::class])
class AppModule


/**
 * Модуль для создания [ViewModelFactory]
 */
@Module(includes = [DomainModule::class])
class ViewModuleFactoryModule() {
    @Provides
    fun provideViewModelFactory(
        application: Application,
        recordsInteractor: RecordsInteractor
    ): ViewModelFactory {
        return ViewModelFactory(application, recordsInteractor)
    }
}

/**
 * Модуль domain слоя
 */
@Module(includes = [AppBindModule::class])
class DomainModule() {

    @Provides
    fun provideConverter() = RecordItemConverterImpl()

    @Provides
    fun provideStorageData() = StorageDataImpl()

    @Provides
    fun provideRecordsRepository(
        converter: IRecordItemConverter,
        storage: IStorageData
    ) = RecordsRepositoryImpl(converter, storage)

    @Provides
    fun provideRecordsInteractor(
        repository: IRecordsRepository
    ) = RecordsInteractor(repository)

}

/**
 * Модуль биндинга
 */
@Module
abstract class AppBindModule {

    @Binds
    abstract fun bindRecordItemConverterImpl_to_IRecordItemConverter(recordItemConverterImpl: RecordItemConverterImpl): IRecordItemConverter

    @Binds
    abstract fun bindStorageDataImpl_to_IStorageData(storageDataImpl: StorageDataImpl): IStorageData

    @Binds
    abstract fun bindRecordsRepositoryImpl_to_IRecordsRepository(recordsRepositoryImpl: RecordsRepositoryImpl): IRecordsRepository
}