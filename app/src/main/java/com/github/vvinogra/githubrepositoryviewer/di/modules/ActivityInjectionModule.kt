package com.github.vvinogra.githubrepositoryviewer.di.modules

import com.github.vvinogra.githubrepositoryviewer.ui.login.LoginActivity
import com.github.vvinogra.githubrepositoryviewer.ui.repohistory.RepoHistoryActivity
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.SearchRepoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectionModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchRepoActivity(): SearchRepoActivity

    @ContributesAndroidInjector
    abstract fun contributeRepoHistoryActivity(): RepoHistoryActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity
}