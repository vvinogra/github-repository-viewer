package com.github.vvinogra.githubrepositoryviewer.di.modules

import android.app.Application
import androidx.room.Room
import com.github.vvinogra.githubrepositoryviewer.data.local.DatabaseConstants
import com.github.vvinogra.githubrepositoryviewer.data.local.room.GithubDatabase
import com.github.vvinogra.githubrepositoryviewer.data.local.room.RepositoryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application): GithubDatabase {
        return Room.databaseBuilder(
            application,
            GithubDatabase::class.java,
            DatabaseConstants.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesProductDao(GithubDatabase: GithubDatabase): RepositoryDao {
        return GithubDatabase.repositoryDao()
    }
}