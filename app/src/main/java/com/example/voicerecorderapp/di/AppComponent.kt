package com.example.voicerecorderapp.di

import android.app.Application
import com.example.voicerecorderapp.presentation.fragments.AllRecordsFragment
import com.example.voicerecorderapp.presentation.fragments.VoiceRecorderFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Базовый компонент приложения
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    val allRecordsComponent: AllRecordsComponent
    val voiceRecorderComponent: VoiceRecorderComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}

/**
 * Субкомпонент фрагмента [AllRecordsFragment]
 */
@Subcomponent
interface AllRecordsComponent {
    fun inject(allRecordsFragment: AllRecordsFragment)
}

/**
 * Субкомпонент фрагмента [VoiceRecorderFragment]
 */
@Subcomponent
interface VoiceRecorderComponent {
    fun inject(voiceRecorderFragment: VoiceRecorderFragment)
}