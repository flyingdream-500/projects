package com.example.finalproject.presentation.viewmodel

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.SettingsInteractorImpl
import com.example.finalproject.domain.usecase.UserInteractorImpl
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.user.User
import com.example.finalproject.presentation.navigation.SettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

/**
 * View Model для экрана [SettingsScreen]
 *
 * @param settingsInteractor интерактор для взаимодействия с настройками приложения, реализация [SettingsInteractorImpl]
 * @param userInteractor интерактор для взаимодействия с данными пользователя, реализация [UserInteractorImpl]
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    // Live Data User
    private val userLiveData = MutableLiveData<User>()
    private val userErrorLiveData = MutableLiveData<Throwable>()

    //Observe User
    fun observeUser() = userLiveData as LiveData<User>
    fun observeUserError() = userErrorLiveData as LiveData<Throwable>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()


    /**
     * Получаем из [SharedPreferences], установлен ли флажок аутентификации
     */
    fun getSettingsAuth(): Boolean {
        return settingsInteractor.getAuthCheck()
    }

    /**
     * Сохраняем в [SharedPreferences] флажок аутентификации
     */
    fun setSettingsAuth(check: Boolean) {
        return settingsInteractor.setAuthCheck(check)
    }

    /**
     * Получаем данные о пользователе из БД
     */
    fun getUser() {
        val disposable = userInteractor.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { user ->
                    userLiveData.value = user
                },
                { throwable ->
                    userErrorLiveData.value = throwable
                }
            )
        compositeDisposable.add(disposable)
    }


    /**
     * Сохраняем данные о пользователе в БД
     */
    fun saveUser(user: User) {
        val disposable = userInteractor.updateAndGetUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updatedUser ->
                    userLiveData.value = updatedUser
                },
                { throwable ->
                    userErrorLiveData.value = throwable
                }
            )
        compositeDisposable.add(disposable)
    }

    /**
     * Сохраняем файл аватара пользователя в Internal Storage
     */
    fun saveAvatarFile(uri: Uri, targetFile: File) {
        val disposable = settingsInteractor.saveAvatarFile(uri, targetFile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //success adding
                },
                { throwable ->
                    userErrorLiveData.value = throwable
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}