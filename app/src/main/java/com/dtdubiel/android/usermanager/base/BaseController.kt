package com.dtdubiel.android.usermanager.base

import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller
import com.dtdubiel.android.usermanager.di.Name.MAIN_NAVIGATION
import com.dtdubiel.android.usermanager.di.Scope.ACTIVITY_SCOPE
import com.dtdubiel.android.usermanager.navigation.Navigation
import com.dtdubiel.android.usermanager.utils.defaultSchedulers
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Flowable
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named


abstract class BaseController(args: Bundle? = null) : Controller(args), KoinComponent, IBaseView {

    protected open val navigation: Navigation by lazy {
        getKoin().getScope(ACTIVITY_SCOPE).get<Navigation>(named(MAIN_NAVIGATION))
    }

    init {
        addLifecycleListener(object : LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                super.postCreateView(controller, view)
                onViewCreated(view)
            }
        })
    }

    override fun showError(error: String) {
        view?.let {
            Snackbar.make(it, error, Snackbar.LENGTH_LONG).show()
        }

    }

    protected open fun onViewCreated(view: View) {

    }

    override fun showProgress(show: Boolean) {
        navigation.showProgress(show)
    }

}
