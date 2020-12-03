package com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.github.vvinogra.githubrepositoryviewer.data.network.model.SearchRepoResponse
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.GithubApi
import com.github.vvinogra.githubrepositoryviewer.data.network.model.Repository

class SearchRepoDataSource(
    private val api: GithubApi,
    private val searchQuery: String
) : PageKeyedDataSource<Int, Repository>() {

    companion object {
        const val PAGE_SIZE = 30
        const val SIZE_PER_REQUEST = PAGE_SIZE / 2
    }

    val initialState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        val currentPage = 0
        val nextPage = currentPage + 1

        api.searchRepositories(searchQuery, currentPage, SIZE_PER_REQUEST)
            .zipWith(api.searchRepositories(searchQuery, nextPage, SIZE_PER_REQUEST),
                { firstResult: SearchRepoResponse, secondResult: SearchRepoResponse ->
                    firstResult.items + secondResult.items
                })
            .doOnSubscribe {
                initialState.postValue(NetworkState.LOADING)
            }
            .blockingSubscribe({
                callback.onResult(it, null, nextPage + 1)

                initialState.postValue(NetworkState.LOADED)
            }, {
                initialState.postValue(NetworkState.error(it.message))
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        api.searchRepositories(searchQuery, currentPage, SIZE_PER_REQUEST)
            .zipWith(api.searchRepositories(searchQuery, nextPage, SIZE_PER_REQUEST),
                { firstResult: SearchRepoResponse, secondResult: SearchRepoResponse ->
                    firstResult.items + secondResult.items
                })
            .subscribe({
                callback.onResult(it, nextPage + 1)
            }, {
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        // ignored, since we only ever append to our initial load
    }

    class SearchRepoDataSourceFactory(
        private val api: GithubApi,
        private val searchQuery: String
    ) : DataSource.Factory<Int, Repository>() {

        val source = MutableLiveData<SearchRepoDataSource>()

        override fun create(): DataSource<Int, Repository> {
            val dataSource = SearchRepoDataSource(api, searchQuery)

            source.postValue(dataSource)

            return dataSource
        }
    }
}