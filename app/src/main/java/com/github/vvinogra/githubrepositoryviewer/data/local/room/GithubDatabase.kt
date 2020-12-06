package com.github.vvinogra.githubrepositoryviewer.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.vvinogra.githubrepositoryviewer.data.local.LocalStorageConstants
import com.github.vvinogra.githubrepositoryviewer.data.local.model.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = LocalStorageConstants.DB_VERSION, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}

