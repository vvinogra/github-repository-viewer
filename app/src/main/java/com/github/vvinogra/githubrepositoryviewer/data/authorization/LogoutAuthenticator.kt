package com.github.vvinogra.githubrepositoryviewer.data.authorization

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class LogoutAuthenticator @Inject constructor(
    private val authSessionService: AuthSessionService
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        authSessionService.logoutUser()

        return null
    }
}