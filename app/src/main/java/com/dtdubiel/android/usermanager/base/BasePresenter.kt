package com.dtdubiel.android.usermanager.base

import androidx.annotation.CallSuper
import com.dtdubiel.android.usermanager.utils.addTo
import com.dtdubiel.android.usermanager.utils.defaultSchedulers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

interface IBasePresenter {
    fun onStart()
    fun onDestroy()
}

abstract class BasePresenter : IBasePresenter {

    private val disposables = CompositeDisposable()

    override fun onStart() {
    }

    @CallSuper
    override fun onDestroy() {
        disposables.dispose()
    }

    protected fun <W, T : IBaseView, R: Flowable<W>> T.lockAndLoad(
        call: R, onSuccess: (W) -> Unit, onError: (Throwable) -> Unit
    ) =
        call
            .defaultSchedulers()
            .doOnSubscribe { showProgress(true) }
            .doOnComplete { showProgress(false) }
            .subscribe(
                { result -> onSuccess(result) },
                { error -> onError(error) }
            )
            .addTo(disposables)


    protected fun <T : IBaseView, R: Completable> T.lockAndLoad(
        call: R, onSuccess: () -> Unit, onError: (Throwable) -> Unit
    ) =
        call
            .defaultSchedulers()
            .doOnSubscribe { showProgress(true) }
            .doOnComplete { showProgress(false) }
            .subscribe(
                { onSuccess() },
                { error -> onError(error) }
            )
            .addTo(disposables)

}
