package com.example.finalproject.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.domain.usecase.interfaces.SettingsInteractor
import com.example.finalproject.domain.usecase.interfaces.UserInteractor
import com.example.finalproject.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val userLiveData = MutableLiveData<User>()
    private val userErrorLiveData = MutableLiveData<Throwable>()
    fun observeUser() = userLiveData as LiveData<User>
    fun observeUserError() = userErrorLiveData as LiveData<Throwable>

    // CompositeDisposable для подписки и отписки получения данных в Rx
    private val compositeDisposable = CompositeDisposable()


    fun getSettingsAuth(): Boolean {
        return settingsInteractor.getAuthCheck()
    }

    fun setSettingsAuth(check: Boolean) {
        return settingsInteractor.setAuthCheck(check)
    }

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


    fun saveUser(user: User) {
        val disposable = userInteractor.updateUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    getUser()
                },
                { throwable ->
                    userErrorLiveData.value = throwable
                }
            )
        compositeDisposable.add(disposable)
    }

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
}