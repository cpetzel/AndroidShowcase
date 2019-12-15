package com.petzel.dev.android.androidshowcase.di

import android.app.Application
import com.facebook.flipper.core.FlipperClient
import com.petzel.dev.android.androidshowcase.repository.RedditClient
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, FlipperModule::class, NetworkModule::class])
@Singleton
interface AppComponent {

    fun flipperClient(): FlipperClient

    fun redditClient(): RedditClient

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        // @BindsInstance replaces Builder appModule(AppModule appModule)
        // And removes Constructor with Application AppModule(Application)

        @BindsInstance
        fun application(application: Application): Builder
    }

}