package com.github.vvinogra.githubrepositoryviewer.di.modules

import com.github.vvinogra.githubrepositoryviewer.di.scopes.PerActivity
import com.github.vvinogra.githubrepositoryviewer.ui.repohistory.view.RepoHistoryActivity
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.view.SearchRepoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectionModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeSearchRepoActivity(): SearchRepoActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeRepoHistoryActivity(): RepoHistoryActivity
}