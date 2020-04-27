package com.example.currency.view_model

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class BaseViewModel : ViewModel(), ThreadProvider {

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun dispose() {
        compositeDisposable.clear()
    }
}


interface ThreadProvider {
    fun getSchedulerIO(): Scheduler {
        return Schedulers.io()
    }

    fun getMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}