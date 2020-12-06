package com.github.vvinogra.githubrepositoryviewer.domain.authorization

import com.github.vvinogra.githubrepositoryviewer.data.authorization.AuthSessionService
import com.github.vvinogra.githubrepositoryviewer.data.authorization.toAuthorizationInfo
import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.GithubApi
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationDomain @Inject constructor(
    private val api: GithubApi,
    private val authSessionService: AuthSessionService
) {
    val loginStateObservable: Observable<Boolean>
        get() = authSessionService.loginStateObservable

    val isLoggedIn: Boolean
        get() = authSessionService.isLoggedIn

    fun logoutUser() {
        authSessionService.logoutUser()
    }

    fun authorizeUser(temporaryCode: String): Completable {
        return api.authorizeUser(
            NetworkConstants.APP_CLIENT_ID,
            NetworkConstants.APP_CLIENT_SECRET,
            temporaryCode
        ).flatMapCompletable {
            Completable.fromAction {
                authSessionService.loginUser(it.toAuthorizationInfo())
            }
        }
    }
}
