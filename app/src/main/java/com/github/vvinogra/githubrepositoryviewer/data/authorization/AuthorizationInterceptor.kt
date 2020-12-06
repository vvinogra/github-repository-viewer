package com.github.vvinogra.githubrepositoryviewer.data.authorization

import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authSessionService: AuthSessionService
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()

        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest() = when (AuthorizationType.fromRequest(this)) {
        AuthorizationType.ACCESS_TOKEN -> signWithFreshAccessToken()
        AuthorizationType.NONE -> this
    }

    private fun Request.signWithFreshAccessToken(): Request {
        val accessToken = authSessionService.authorizationToken
        val tokenType = authSessionService.authorizationTokenType

        return newBuilder()
            .header(NetworkConstants.AUTHORIZATION_HEADER, "$tokenType $accessToken")
            .build()
    }
}