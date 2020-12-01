package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation

import com.github.vvinogra.githubviewer.data.network.model.Repository

data class RepositoryPresentation(
    val description: String,
    val id: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repositoryName: String,
    val stargazers: Int
)

fun Repository.toRepositoryPresentation() = RepositoryPresentation(
    description = description,
    id = id,
    ownerName = owner.login,
    ownerAvatarUrl = owner.avatarUrl,
    repositoryName = fullName,
    stargazers = stargazersCount
)