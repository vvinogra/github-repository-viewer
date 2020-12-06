package com.github.vvinogra.githubrepositoryviewer.data.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.github.vvinogra.githubrepositoryviewer.data.local.model.RepositoryEntity
import io.reactivex.rxjava3.core.Completable

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository ORDER BY created_at DESC")
    fun getRepositories(): DataSource.Factory<Int, RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(repositoryEntity: RepositoryEntity): Completable

    @Query("DELETE FROM repository where id NOT IN (SELECT id from repository ORDER BY created_at DESC LIMIT :limitCount)")
    fun limitItems(limitCount: Int): Completable
}