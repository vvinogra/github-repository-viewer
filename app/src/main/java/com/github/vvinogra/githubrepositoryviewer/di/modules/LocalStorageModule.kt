package com.github.vvinogra.githubrepositoryviewer.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.github.vvinogra.githubrepositoryviewer.data.local.LocalStorageConstants
import com.github.vvinogra.githubrepositoryviewer.data.local.room.GithubDatabase
import com.github.vvinogra.githubrepositoryviewer.data.local.room.RepositoryDao
import com.github.vvinogra.githubrepositoryviewer.di.annotations.AuthorizationSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalStorageModule {
    @Singleton
    @Provides
    @AuthorizationSharedPreferences
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            LocalStorageConstants.AUTHORIZATION_SHARED_PREF_FILENAME, Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application): GithubDatabase {
        return Room.databaseBuilder(
            application,
            GithubDatabase::class.java,
            LocalStorageConstants.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesProductDao(GithubDatabase: GithubDatabase): RepositoryDao {
        return GithubDatabase.repositoryDao()
    }
}