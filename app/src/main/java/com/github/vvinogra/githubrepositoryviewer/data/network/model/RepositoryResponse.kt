package com.github.vvinogra.githubrepositoryviewer.data.network.model

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("description")
    val description: String?,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("owner")
    val owner: UserResponse,
    @SerializedName("stargazers_count")
    val stargazersCount: Int
)