package com.dtdubiel.android.usermanager.navigation

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import timber.log.Timber

private const val FADE_DURATION_TIME = 100L

class MainNavigation(
    private val mainRouter: Router,
    private val dialogRouter: Router,
    private val dialogController: ViewGroup,
    private val uiController: UIController
) : Navigation {

    init {
        setupDialogRouter()
    }

    override fun show(controller: Controller) {
        Timber.d("Navigation showing $controller")
        hideViews()
        when (controller) {
            is Dialog -> dialogRouter
            else -> mainRouter.also {
                dialogRouter.takeIf { it.backstackSize > 0 }?.popCurrentController()
                dialogRouter.backstack.clear()
                dialogController.isVisible = false
            }
        }.takeIf { it.currentController()?.javaClass != controller.javaClass }
            ?.apply {
                push(controller)
            }
    }

    override fun onBack() {
        dialogRouter.takeIf { it.backstack.isNotEmpty() }?.handleBack()
            ?: mainRouter.handleBack()
    }

    override fun showProgress(show: Boolean) {
        uiController.showProgress(show)
    }

    private fun setupDialogRouter() {
        dialogRouter.apply {
            setPopsLastView(true)
            onChangeStart {
                it?.let {
                    dialogController.isVisible = true
                } ?: dialogController.apply {
                    isVisible = false
                    dialogRouter.takeIf { it.backstackSize > 0 }?.popCurrentController()
                    dialogRouter.backstack.clear()
                }
            }
        }
    }

    private fun hideViews() {
        uiController.showProgress(false)
    }

    private fun Router.currentController() = backstack.lastOrNull()?.controller


    private fun Router.push(controller: Controller, fadeDuration: Long = FADE_DURATION_TIME) {
        pushController(
            RouterTransaction
                .with(controller)
                .apply {
                    pushChangeHandler(FadeChangeHandler(fadeDuration))
                    popChangeHandler(FadeChangeHandler(fadeDuration))
                }
        )
    }

}

fun Router.onChangeStart(onChange: (Controller?) -> Unit) =
    addChangeListener(object : ControllerChangeHandler.ControllerChangeListener {
        override fun onChangeStarted(
            to: Controller?,
            from: Controller?,
            isPush: Boolean,
            container: ViewGroup,
            handler: ControllerChangeHandler
        ) {
            to?.let {
                onChange(it)
            }
        }

        override fun onChangeCompleted(
            to: Controller?,
            from: Controller?,
            isPush: Boolean,
            container: ViewGroup,
            handler: ControllerChangeHandler
        ) {
        }
    })
