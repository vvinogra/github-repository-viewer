package com.github.vvinogra.githubrepositoryviewer.di.modules;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageModule {
    @Singleton
    @Provides
    Picasso providePicasso() {
        return Picasso.get();
    }
}
