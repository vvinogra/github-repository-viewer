package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiGithubVersionInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return if (request.url.toString().startsWith(NetworkConstants.BASE_GITHUB_API_URL)) {
            val clone = request.newBuilder().run {
                addHeader(
                    NetworkConstants.ACCEPT_HEADER,
                    NetworkConstants.GITHUB_API_VERSION_HEADER
                )

                build()
            }

            chain.proceed(clone)
        } else {
            chain.proceed(request)
        }
    }
}