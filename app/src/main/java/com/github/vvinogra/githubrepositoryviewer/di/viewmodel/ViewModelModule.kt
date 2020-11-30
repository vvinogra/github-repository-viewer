package com.github.vvinogra.githubrepositoryviewer.di.viewmodel

import androidx.lifecycle.ViewModel
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
}