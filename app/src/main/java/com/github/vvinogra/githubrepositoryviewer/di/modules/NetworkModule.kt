package com.github.vvinogra.githubrepositoryviewer.di.modules

import com.github.vvinogra.githubrepositoryviewer.BuildConfig
import com.github.vvinogra.githubrepositoryviewer.data.network.NetworkConstants
import com.github.vvinogra.githubrepositoryviewer.data.authorization.AuthorizationInterceptor
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.GithubApi
import com.github.vvinogra.githubrepositoryviewer.data.network.retrofit.ApiGithubVersionInterceptor
import com.github.vvinogra.githubrepositoryviewer.data.authorization.LogoutAuthenticator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        apiGithubVersionInterceptor: ApiGithubVersionInterceptor,
        logoutAuthenticator: LogoutAuthenticator
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(apiGithubVersionInterceptor)
        builder.addInterceptor(authorizationInterceptor)
        builder.authenticator(logoutAuthenticator)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(NetworkConstants.BASE_GITHUB_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }
}