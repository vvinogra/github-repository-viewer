package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response

class GithubVersionApiInterceptor : Interceptor {
    companion object {
        private const val ACCEPT_HEADER = "Accept"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader(ACCEPT_HEADER, NetworkConstants.GITHUB_API_VERSION_HEADER)

        return chain.proceed(request.build())
    }
}