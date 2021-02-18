package com.dtdubiel.android.usermanager.di

import android.view.ViewGroup
import com.bluelinelabs.conductor.Router
import com.dtdubiel.android.usermanager.di.Name.MAIN_NAVIGATION
import com.dtdubiel.android.usermanager.di.Scope.ACTIVITY_SCOPE
import com.dtdubiel.android.usermanager.navigation.MainNavigation
import com.dtdubiel.android.usermanager.navigation.Navigation
import com.dtdubiel.android.usermanager.navigation.UIController
import org.koin.core.qualifier.named
import org.koin.dsl.module

val navigationModule = module {
    scope(named(ACTIVITY_SCOPE)) {
        scoped<Navigation>(named(MAIN_NAVIGATION)) { (
                                                         mainRouter: Router,
                                                         dialogGroup: Pair<Router, ViewGroup>,
                                                         uiController: UIController
                                                     ) ->
            MainNavigation(
                mainRouter,
                dialogGroup.first,
                dialogGroup.second,
                uiController
            )
        }
    }

}