package com.github.vvinogra.githubrepositoryviewer.data.network.model

import com.github.vvinogra.githubviewer.data.network.model.Repository
import com.google.gson.annotations.SerializedName

data class SearchRepoResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Repository>,
    @SerializedName("total_count")
    val totalCount: Int
)