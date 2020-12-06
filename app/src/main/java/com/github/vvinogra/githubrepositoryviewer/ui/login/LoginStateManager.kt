package com.github.vvinogra.githubrepositoryviewer.ui.login

import android.app.Application
import android.content.Intent
import com.github.vvinogra.githubrepositoryviewer.data.utils.RxSchedulers
import com.github.vvinogra.githubrepositoryviewer.domain.authorization.AuthorizationDomain
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.SearchRepoActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginStateManager @Inject constructor(
    private val application: Application,
    private val authorizationDomain: AuthorizationDomain,
    private val rxSchedulers: RxSchedulers
) {
    private var isInitialized = false

    @Synchronized
    fun initialize() {
        if (!isInitialized) {
            authorizationDomain.loginStateObservable
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.main())
                .subscribe({
                    onLoginStateChange(it)
                }, {})

            isInitialized = true
        }
    }

    private fun onLoginStateChange(isLoggedIn: Boolean) {
        val component = if (isLoggedIn)
            SearchRepoActivity::class.java
        else
            LoginActivity::class.java

        val intent = Intent(application, component)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        application.startActivity(intent)
    }
}