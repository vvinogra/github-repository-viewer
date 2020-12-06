package com.github.vvinogra.githubrepositoryviewer.data.network.model
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("login")
    val login: String
)
