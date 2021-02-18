package com.dtdubiel.android.usermanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.dtdubiel.android.usermanager.di.Name.MAIN_NAVIGATION
import com.dtdubiel.android.usermanager.di.Scope.ACTIVITY_SCOPE
import com.dtdubiel.android.usermanager.feature.users_list.UsersScreen
import com.dtdubiel.android.usermanager.navigation.Navigation
import com.dtdubiel.android.usermanager.navigation.UIController
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity(), UIController {

    private val navigation: Navigation by lazy {
        getKoin().getScope(ACTIVITY_SCOPE).get<Navigation>(named(MAIN_NAVIGATION)) {
            parametersOf(
                Conductor.attachRouter(this, findViewById(R.id.mainViewContainer), null),
                Pair(
                    Conductor.attachRouter(this, findViewById(R.id.dialogContainer), null),
                    findViewById<ChangeHandlerFrameLayout>(R.id.dialogContainer)
                ),
                this
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getKoin().getOrCreateScope(ACTIVITY_SCOPE, named(ACTIVITY_SCOPE))
        navigation.show(UsersScreen())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigation.onBack()
    }

    override fun showProgress(show: Boolean) {
        findViewById<View>(R.id.progress).isVisible = show
    }

}
