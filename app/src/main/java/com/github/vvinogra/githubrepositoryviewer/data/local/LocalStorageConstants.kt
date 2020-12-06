package com.github.vvinogra.githubrepositoryviewer.data.local

object LocalStorageConstants {
    const val AUTHORIZATION_SHARED_PREF_FILENAME = "AUTHORIZATION_PERSISTENT"

    const val DB_VERSION = 1
    const val DB_NAME = "github.db"

    object Repository {
        const val TABLE_NAME = "repository"
    }
}