package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import com.github.vvinogra.githubrepositoryviewer.data.network.model.SearchRepoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String,
                            @Query("page") page: Int,
                            @Query("per_page") perPage: Int): Single<SearchRepoResponse>
}