package com.github.vvinogra.githubrepositoryviewer.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.vvinogra.githubrepositoryviewer.data.local.DatabaseConstants.Repository.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RepositoryEntity(
    val description: String,
    @PrimaryKey
    val id: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val repositoryName: String,
    val stargazers: Int,
    val webPageUrl: String
)