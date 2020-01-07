package com.petzel.dev.android.androidshowcase.di

import android.app.Application
import android.os.Handler
import com.facebook.flipper.core.FlipperClient
import com.petzel.dev.android.androidshowcase.MainActivityComponent
import com.petzel.dev.android.androidshowcase.database.PostsDatabase
import com.petzel.dev.android.androidshowcase.repository.RedditApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CacheModule::class,
        AppModule::class,
        FlipperModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    fun flipperClient(): FlipperClient
    fun db(): PostsDatabase
    fun redditApi(): RedditApi
    fun mainHandler(): Handler

    fun mainActivityComponentFactory(): MainActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}