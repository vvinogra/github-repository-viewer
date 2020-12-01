package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class GithubVersionApiInterceptor : Interceptor {
    companion object {
        private const val ACCEPT_HEADER = "Accept"
        private const val GITHUB_API_VERSION_HEADER = "application/vnd.github.v3+json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader(ACCEPT_HEADER, GITHUB_API_VERSION_HEADER)

        return chain.proceed(request.build())
    }
}