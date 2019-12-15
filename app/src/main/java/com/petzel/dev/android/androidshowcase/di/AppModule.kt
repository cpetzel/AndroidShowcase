package com.petzel.dev.android.androidshowcase.di

import android.content.Context
import com.petzel.dev.android.androidshowcase.repository.RedditClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


// binds
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRedditClient(retrofit: Retrofit): RedditClient {
        return retrofit.create(RedditClient::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideMovieApi(retrofit: Retrofit): MSMovieApi {
//        return retrofit.create(MSMovieApi::class.java)
//    }
}