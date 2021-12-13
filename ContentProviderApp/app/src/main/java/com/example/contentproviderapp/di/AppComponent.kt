package com.example.contentproviderapp.di

import android.app.Application
import com.example.contentproviderapp.data.datastore.provider.DictionaryContentProvider
import com.example.contentproviderapp.presentation.navigation.AddDictionaryItemFragment
import com.example.contentproviderapp.presentation.navigation.DictionaryFragment
import com.example.contentproviderapp.presentation.navigation.PickImageFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton



@Singleton
@Component(modules = [ViewModelFactoryModule::class])
interface AppComponent {
    val dictionaryFragmentComponent: DictionaryFragmentComponent
    val addItemFragmentComponent: AddItemFragmentComponent
    val pickImageFragmentComponent: PickImageFragmentComponent
    val dictionaryContentProviderComponent: DictionaryContentProviderComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}

/**
 * Субкомпонент фрагмента [DictionaryFragment]
 */
@Subcomponent
interface DictionaryContentProviderComponent {
    fun inject(dictionaryContentProvider: DictionaryContentProvider)
}

/**
 * Субкомпонент фрагмента [DictionaryFragment]
 */
@Subcomponent
interface DictionaryFragmentComponent {
    fun inject(dictionaryFragment: DictionaryFragment)
}

/**
 * Субкомпонент фрагмента [CurrencyListScreen]
 */
@Subcomponent
interface AddItemFragmentComponent {
    fun inject(addDictionaryItemFragment: AddDictionaryItemFragment)
}

/**
 * Субкомпонент фрагмента [CurrencyDetailScreen]
 */
@Subcomponent
interface PickImageFragmentComponent {
    fun inject(pickImageFragment: PickImageFragment)
}