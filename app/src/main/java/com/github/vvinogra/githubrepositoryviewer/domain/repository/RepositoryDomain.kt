package com.github.vvinogra.githubrepositoryviewer.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.github.vvinogra.githubrepositoryviewer.data.local.room.RepositoryDao
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.GithubApi
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class RepositoryDomain @Inject constructor(
    private val api: GithubApi,
    private val dao: RepositoryDao
) {
    companion object {
        private const val HISTORY_SIZE = 20
    }

    fun searchRepositories(query: String): Listing<Repository> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(SearchRepoDataSource.PAGE_SIZE)
            .setPageSize(SearchRepoDataSource.PAGE_SIZE)
            .build()

        val factory = SearchRepoDataSource.SearchRepoDataSourceFactory(api, query)

        val livePagedList =
            LivePagedListBuilder(factory.map { it.toRepositoryPresentation() }, config)
                .build()

        return Listing(
            pagedList = livePagedList,
            refresh = { factory.source.value?.invalidate() },
            refreshState = Transformations.switchMap(factory.source) { it.initialState },
            networkState = Transformations.switchMap(factory.source) { it.networkState }
        )
    }

    fun addItemToHistory(repository: Repository): Completable {
        return dao.addItem(repository.toRepositoryEntity())
            .andThen(dao.limitItems(HISTORY_SIZE))
    }

    fun loadRepositoryHistory(): LiveData<PagedList<Repository>> {
        return dao.getRepositories().map {
            it.toRepositoryPresentation()
        }.toLiveData(pageSize = HISTORY_SIZE)
    }
}