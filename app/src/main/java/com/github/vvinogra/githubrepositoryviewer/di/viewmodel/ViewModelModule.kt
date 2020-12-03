package com.github.vvinogra.githubrepositoryviewer.di.viewmodel

import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.repohistory.viewmodel.RepoHistoryViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.viewmodel.SearchRepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchRepoViewModel::class)
    abstract fun provideSearchRepoViewModel(searchRepoViewModel: SearchRepoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoHistoryViewModel::class)
    abstract fun provideRepoHistoryViewModel(repoHistoryViewModel: RepoHistoryViewModel): ViewModel
}