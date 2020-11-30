package com.github.vvinogra.githubrepositoryviewer.di.modules

import com.github.vvinogra.githubrepositoryviewer.ui.searchrepo.view.SearchRepoActivity
import com.github.vvinogra.githubrepositoryviewer.di.scopes.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectionModule {
    @PerActivity
    @ContributesAndroidInjector()
    abstract fun contributeSearchRepoActivity() : SearchRepoActivity

//    @PerActivity
//    @ContributesAndroidInjector(modules = [UserModule::class])
//    abstract fun contributeUserActivity() : UserActivity
}