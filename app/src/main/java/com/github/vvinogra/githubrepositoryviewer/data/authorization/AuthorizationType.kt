package com.github.vvinogra.githubrepositoryviewer.data.authorization

import okhttp3.Request

enum class AuthorizationType {
    ACCESS_TOKEN,
    NONE;

    companion object {
        fun fromRequest(request: Request): AuthorizationType =
            request.tag(AuthorizationType::class.java) ?: ACCESS_TOKEN
    }
}
