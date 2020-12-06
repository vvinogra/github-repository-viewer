package com.github.vvinogra.githubrepositoryviewer

import androidx.multidex.MultiDexApplication
import com.github.vvinogra.githubrepositoryviewer.di.DaggerAppComponent
import com.github.vvinogra.githubrepositoryviewer.ui.login.LoginStateManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class AppDelegate : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var loginStateManager: LoginStateManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        loginStateManager.initialize()
    }

    override fun androidInjector(): AndroidInjector<Any?> {
        return dispatchingAndroidInjector
    }
}