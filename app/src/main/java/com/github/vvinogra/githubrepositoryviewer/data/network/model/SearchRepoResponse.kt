package com.github.vvinogra.githubrepositoryviewer.data.network.model

import com.google.gson.annotations.SerializedName

data class SearchRepoResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<RepositoryResponse>,
    @SerializedName("total_count")
    val totalCount: Int
)