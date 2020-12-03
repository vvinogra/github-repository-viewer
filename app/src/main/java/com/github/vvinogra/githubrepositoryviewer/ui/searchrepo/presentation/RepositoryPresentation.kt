package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation

import com.github.vvinogra.githubrepositoryviewer.data.local.model.RepositoryEntity
import com.github.vvinogra.githubrepositoryviewer.data.network.model.Repository

data class RepositoryPresentation(
    val description: String,
    val id: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repositoryName: String,
    val stargazers: Int,
    val webPageUrl: String
)

fun Repository.toRepositoryPresentation() = RepositoryPresentation(
    description = description,
    id = id,
    ownerName = owner.login,
    ownerAvatarUrl = owner.avatarUrl,
    repositoryName = fullName,
    stargazers = stargazersCount,
    webPageUrl = htmlUrl
)

fun RepositoryEntity.toRepositoryPresentation() = RepositoryPresentation(
    description = description,
    id = id,
    ownerName = ownerName,
    ownerAvatarUrl = ownerAvatarUrl,
    repositoryName = repositoryName,
    stargazers = stargazers,
    webPageUrl = webPageUrl
)

fun RepositoryPresentation.toRepositoryEntity() = RepositoryEntity(
    description = description,
    id = id,
    ownerName = ownerName,
    ownerAvatarUrl = ownerAvatarUrl,
    repositoryName = repositoryName,
    stargazers = stargazers,
    webPageUrl = webPageUrl
)
