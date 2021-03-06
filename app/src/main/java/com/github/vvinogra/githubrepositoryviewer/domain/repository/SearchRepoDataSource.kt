package com.github.vvinogra.githubrepositoryviewer.domain.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.github.vvinogra.githubrepositoryviewer.data.network.model.SearchRepoResponse
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.GithubApi
import com.github.vvinogra.githubrepositoryviewer.data.network.model.RepositoryResponse

class SearchRepoDataSource(
    private val api: GithubApi,
    private val searchQuery: String
) : PageKeyedDataSource<Int, RepositoryResponse>() {

    companion object {
        const val PAGE_SIZE = 30
        const val SIZE_PER_REQUEST = PAGE_SIZE / 2
    }

    val initialState = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RepositoryResponse>
    ) {
        val firstPage = 1
        val nextPage = firstPage + 1

        api.searchRepositories(searchQuery, firstPage, SIZE_PER_REQUEST)
            .zipWith(api.searchRepositories(searchQuery, nextPage, SIZE_PER_REQUEST),
                { firstResult: SearchRepoResponse, secondResult: SearchRepoResponse ->
                    firstResult.items + secondResult.items
                })
            .doOnSubscribe {
                postInitialAndNetworkState(NetworkState.LOADING)
            }
            .blockingSubscribe({
                callback.onResult(it, null, nextPage + 1)

                postInitialAndNetworkState(NetworkState.LOADED)
            }, {
                postInitialAndNetworkState(NetworkState.error(it.message))
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryResponse>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        api.searchRepositories(searchQuery, currentPage, SIZE_PER_REQUEST)
            .zipWith(api.searchRepositories(searchQuery, nextPage, SIZE_PER_REQUEST),
                { firstResult: SearchRepoResponse, secondResult: SearchRepoResponse ->
                    firstResult.items + secondResult.items
                })
            .doOnSubscribe {
                networkState.postValue(NetworkState.LOADING)
            }
            .subscribe({
                callback.onResult(it, nextPage + 1)

                networkState.postValue(NetworkState.LOADED)
            }, {
                networkState.postValue(NetworkState.error(it.message))
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RepositoryResponse>) {
        // ignored, since we only ever append to our initial load
    }

    private fun postInitialAndNetworkState(state: NetworkState) {
        initialState.postValue(state)
        networkState.postValue(state)
    }

    class SearchRepoDataSourceFactory(
        private val api: GithubApi,
        private val searchQuery: String
    ) : DataSource.Factory<Int, RepositoryResponse>() {

        val source = MutableLiveData<SearchRepoDataSource>()

        override fun create(): DataSource<Int, RepositoryResponse> {
            val dataSource = SearchRepoDataSource(api, searchQuery)

            source.postValue(dataSource)

            return dataSource
        }
    }
}