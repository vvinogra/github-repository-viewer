package com.github.vvinogra.githubrepositoryviewer.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.vvinogra.githubrepositoryviewer.data.local.DatabaseConstants
import com.github.vvinogra.githubrepositoryviewer.data.local.model.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = DatabaseConstants.DB_VERSION)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}

