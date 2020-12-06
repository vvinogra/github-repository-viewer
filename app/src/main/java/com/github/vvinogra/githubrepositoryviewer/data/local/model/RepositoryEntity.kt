package com.github.vvinogra.githubrepositoryviewer.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.vvinogra.githubrepositoryviewer.data.local.LocalStorageConstants.Repository.TABLE_NAME
import java.util.*

@Entity(tableName = TABLE_NAME)
data class RepositoryEntity(
    @ColumnInfo(name = "description")
    val description: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "owner_name")
    val ownerName: String,
    @ColumnInfo(name = "owner_avatar_url")
    val ownerAvatarUrl: String,
    @ColumnInfo(name = "repository_name")
    val repositoryName: String,
    @ColumnInfo(name = "stargazers")
    val stargazers: Int,
    @ColumnInfo(name = "web_page_url")
    val webPageUrl: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date
)