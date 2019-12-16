package com.petzel.dev.android.androidshowcase.di

import android.app.Application
import android.os.Handler
import com.facebook.flipper.core.FlipperClient
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.core.Navigator
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


    fun inject(baseFragment: BaseFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        // @BindsInstance replaces Builder appModule(AppModule appModule)
        // And removes Constructor with Application AppModule(Application)

        @BindsInstance
        fun application(application: Application): Builder
    }

}