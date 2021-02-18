package com.dtdubiel.android.usermanager.navigation

import com.bluelinelabs.conductor.Controller

interface Navigation {
    fun showProgress(show: Boolean)
    fun show(controller: Controller)
    fun onBack()
}
