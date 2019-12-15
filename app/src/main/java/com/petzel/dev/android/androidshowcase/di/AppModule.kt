package com.petzel.dev.android.androidshowcase.di

import android.os.Handler
import android.os.Looper
import com.petzel.dev.android.androidshowcase.repository.RedditApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRedditClient(retrofit: Retrofit): RedditApi {
        return retrofit.create(RedditApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMainHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }
}