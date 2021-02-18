package com.dtdubiel.android.usermanager

import android.app.Application
import com.dtdubiel.android.networking.networkingModule
import com.dtdubiel.android.usermanager.di.navigationModule
import com.dtdubiel.android.usermanager.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class UserManagerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UserManagerApplication)
            modules(
                listOf(
                    navigationModule,
                    presenterModule,
                    networkingModule
                )
            )
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
        }
    }

}
