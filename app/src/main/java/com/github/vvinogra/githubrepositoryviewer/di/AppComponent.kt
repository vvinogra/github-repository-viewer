package com.github.vvinogra.githubrepositoryviewer.di

import android.app.Application
import com.github.vvinogra.githubrepositoryviewer.AppDelegate
import com.github.vvinogra.githubrepositoryviewer.di.modules.ActivityInjectionModule
import com.github.vvinogra.githubrepositoryviewer.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityInjectionModule::class,
    ViewModelModule::class
])
interface AppComponent {

    fun inject(appDelegate: AppDelegate)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}