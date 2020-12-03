package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import com.github.vvinogra.githubrepositoryviewer.data.network.model.SearchRepoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET(NetworkConstants.SearchApi.PATH)
    fun searchRepositories(
        @Query(NetworkConstants.SearchApi.Params.QUERY) query: String,
        @Query(NetworkConstants.SearchApi.Params.PAGE) page: Int,
        @Query(NetworkConstants.SearchApi.Params.PER_PAGE) perPage: Int,
        @Query(NetworkConstants.SearchApi.Params.SORT) sort: String = NetworkConstants.SearchApi.SortByOption.STARS
    ): Single<SearchRepoResponse>
}