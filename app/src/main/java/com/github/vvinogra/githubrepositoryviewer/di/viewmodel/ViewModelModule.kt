package com.github.vvinogra.githubrepositoryviewer.di.viewmodel

import androidx.lifecycle.ViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.login.LoginViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.repohistory.RepoHistoryViewModel
import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.SearchRepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun provideLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoHistoryViewModel::class)
    abstract fun provideRepoHistoryViewModel(repoHistoryViewModel: RepoHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchRepoViewModel::class)
    abstract fun provideSearchRepoViewModel(searchRepoViewModel: SearchRepoViewModel): ViewModel
}