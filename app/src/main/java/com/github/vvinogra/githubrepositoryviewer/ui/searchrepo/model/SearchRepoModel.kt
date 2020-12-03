package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.model

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.GithubApi
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation.RepositoryPresentation
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.presentation.toRepositoryPresentation
import javax.inject.Inject

class SearchRepoModel @Inject constructor(
    private val api: GithubApi
) {

    fun searchRepo(query: String): Listing<RepositoryPresentation> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(SearchRepoDataSource.PAGE_SIZE)
            .setPageSize(SearchRepoDataSource.PAGE_SIZE)
            .build()

        val factory = SearchRepoDataSource.SearchRepoDataSourceFactory(api, query)

        val livePagedList = LivePagedListBuilder(factory.map { it.toRepositoryPresentation() }, config)
            .build()

        return Listing(
            pagedList = livePagedList,
            refresh = { factory.source.value?.invalidate() },
            refreshState = Transformations.switchMap(factory.source) { it.initialState }
        )
    }
}