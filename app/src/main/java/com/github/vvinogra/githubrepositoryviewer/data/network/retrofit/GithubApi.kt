package com.github.vvinogra.githubrepositoryviewer.data.network.retrofit

import com.github.vvinogra.githubrepositoryviewer.data.authorization.AuthorizationType
import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import com.github.vvinogra.githubrepositoryviewer.data.network.model.AuthResponse
import com.github.vvinogra.githubrepositoryviewer.data.network.model.SearchRepoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface GithubApi {
    @GET(NetworkConstants.SearchApi.PATH)
    fun searchRepositories(
        @Query(NetworkConstants.SearchApi.Params.QUERY) query: String,
        @Query(NetworkConstants.SearchApi.Params.PAGE) page: Int,
        @Query(NetworkConstants.SearchApi.Params.PER_PAGE) perPage: Int,
        @Query(NetworkConstants.SearchApi.Params.SORT) sort: String = NetworkConstants.SearchApi.SortByOption.STARS
    ): Single<SearchRepoResponse>

    @POST(NetworkConstants.BASE_GITHUB_URL + "/" + NetworkConstants.AuthorizationApi.PATH)
    fun authorizeUser(
        @Query(NetworkConstants.AuthorizationApi.Params.CLIENT_ID) clientId: String,
        @Query(NetworkConstants.AuthorizationApi.Params.CLIENT_SECRET) clientSecret: String,
        @Query(NetworkConstants.AuthorizationApi.Params.CODE) code: String,
        @Header(NetworkConstants.ACCEPT_HEADER) contentType: String = NetworkConstants.AuthorizationApi.DEFAULT_ACCEPT_HEADER,
        @Tag authorization: AuthorizationType = AuthorizationType.NONE
    ): Single<AuthResponse>
}