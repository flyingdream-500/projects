package com.example.neworkrequestsproject.domain.background

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.neworkrequestsproject.domain.UserInteract
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.domain.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RxBackground(private val interact: UserInteract) : Background {

    private var compositeDisposable: CompositeDisposable? = null


    override fun close() {
        compositeDisposable?.clear()
    }

    override fun postPerson(
        postLiveData: MutableLiveData<String>,
        errorLiveData: MutableLiveData<String>,
        person: Person
    ) {
        postObservable(interact, person)
            .subscribe(postObserver(postLiveData, errorLiveData))

    }

    override fun getUsers(
        usersLiveData: MutableLiveData<List<User>>,
        errorLiveData: MutableLiveData<String>
    ) {
        getObservable(interact)
            .subscribe(getObserver(usersLiveData, errorLiveData))
    }


    private fun getObserver(usersLiveData: MutableLiveData<List<User>>, errorsLiveData: MutableLiveData<String>) =
        object : Observer<List<User>> {
            override fun onSubscribe(d: Disposable) {
                compositeDisposable?.add(d)
            }

            override fun onNext(t: List<User>) {
                usersLiveData.value = t
            }

            override fun onError(e: Throwable) {
                errorsLiveData.value = e.message
            }

            override fun onComplete() {}

        }

    private fun postObserver(postsLiveData: MutableLiveData<String>, errorsLiveData: MutableLiveData<String>) =
        object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                compositeDisposable?.add(d)
            }

            override fun onNext(t: String) {
                postsLiveData.value = t
            }

            override fun onError(e: Throwable) {
                errorsLiveData.value = e.message
            }

            override fun onComplete() {}

        }

    private fun getObservable(interact: UserInteract) =
        Observable.fromCallable {
            interact.getList()!!
        }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())

    private fun postObservable(interact: UserInteract, person: Person) =
        Observable.fromCallable {
            interact.postPerson(person)!!
        }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())

}