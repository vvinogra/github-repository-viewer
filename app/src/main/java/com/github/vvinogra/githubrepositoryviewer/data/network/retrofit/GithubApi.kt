package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import com.github.vvinogra.githubrepositoryviewer.data.network.model.SearchRepoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET(NetworkConstants.SearchApi.path)
    fun searchRepositories(
        @Query(NetworkConstants.SearchApi.Params.query) query: String,
        @Query(NetworkConstants.SearchApi.Params.page) page: Int,
        @Query(NetworkConstants.SearchApi.Params.perPage) perPage: Int,
        @Query(NetworkConstants.SearchApi.Params.sort) sort: String = NetworkConstants.SearchApi.SortByOption.STARS
    ): Single<SearchRepoResponse>
}