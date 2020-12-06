package com.github.vvinogra.githubrepositoryviewer.data.authorization

import android.content.SharedPreferences
import com.github.vvinogra.githubrepositoryviewer.di.annotations.AuthorizationSharedPreferences
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthSessionService @Inject constructor(
    @AuthorizationSharedPreferences private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val KEY_ACCESS_TOKEN_TYPE = "ACCESS_TOKEN_TYPE"
    }

    private val loggedInStateSubject = PublishSubject.create<Boolean>()
    val loginStateObservable: Observable<Boolean> = loggedInStateSubject

    val isLoggedIn: Boolean
        get() {
            return authorizationToken != null && authorizationTokenType != null
        }

    val authorizationToken: String?
        get() {
            return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        }

    val authorizationTokenType: String?
        get() {
            return sharedPreferences.getString(KEY_ACCESS_TOKEN_TYPE, null)
        }

    fun loginUser(authInfo: AuthInfo) {
        saveAuthorizationInfo(authInfo)

        loggedInStateSubject.onNext(true)
    }

    fun logoutUser() {
        if (isLoggedIn) {
            sharedPreferences.edit()
                .clear()
                .apply()

            loggedInStateSubject.onNext(false)
        }
    }

    private fun saveAuthorizationInfo(authInfo: AuthInfo) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, authInfo.accessToken)
            .putString(KEY_ACCESS_TOKEN_TYPE, authInfo.tokenType)
            .apply()
    }
}