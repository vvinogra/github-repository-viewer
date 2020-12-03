package com.github.vvinogra.githubrepositoryviewer.data.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.vvinogra.githubrepositoryviewer.data.local.model.RepositoryEntity
import io.reactivex.rxjava3.core.Completable

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository")
    fun getRepositories(): DataSource.Factory<Int, RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(repositoryEntity: RepositoryEntity): Completable

    @Query("DELETE FROM repository where id NOT IN (SELECT id from repository ORDER BY id DESC LIMIT :limitCount)")
    fun limitItems(limitCount: Int): Completable
}