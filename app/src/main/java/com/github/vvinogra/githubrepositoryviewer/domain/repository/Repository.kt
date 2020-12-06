package com.github.vvinogra.githubrepositoryviewer.domain.repository

import com.github.vvinogra.githubrepositoryviewer.data.local.model.RepositoryEntity
import com.github.vvinogra.githubrepositoryviewer.data.network.model.RepositoryResponse
import java.util.*

data class Repository(
    val description: String,
    val id: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repositoryName: String,
    val stargazers: Int,
    val webPageUrl: String
)

fun RepositoryResponse.toRepositoryPresentation() = Repository(
    description = description.orEmpty(),
    id = id,
    ownerName = owner.login,
    ownerAvatarUrl = owner.avatarUrl,
    repositoryName = fullName,
    stargazers = stargazersCount,
    webPageUrl = htmlUrl
)

fun RepositoryEntity.toRepositoryPresentation() = Repository(
    description = description,
    id = id,
    ownerName = ownerName,
    ownerAvatarUrl = ownerAvatarUrl,
    repositoryName = repositoryName,
    stargazers = stargazers,
    webPageUrl = webPageUrl
)

fun Repository.toRepositoryEntity() = RepositoryEntity(
    description = description,
    id = id,
    ownerName = ownerName,
    ownerAvatarUrl = ownerAvatarUrl,
    repositoryName = repositoryName,
    stargazers = stargazers,
    webPageUrl = webPageUrl,
    createdAt = Date()
)
