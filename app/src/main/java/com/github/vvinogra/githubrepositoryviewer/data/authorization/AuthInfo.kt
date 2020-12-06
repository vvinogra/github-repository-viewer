package com.github.vvinogra.githubrepositoryviewer.data.authorization

import com.github.vvinogra.githubrepositoryviewer.data.network.model.AuthResponse

data class AuthInfo(
    val accessToken: String,
    val tokenType: String
)

fun AuthResponse.toAuthorizationInfo() = AuthInfo(
    accessToken = accessToken,
    tokenType = tokenType
)