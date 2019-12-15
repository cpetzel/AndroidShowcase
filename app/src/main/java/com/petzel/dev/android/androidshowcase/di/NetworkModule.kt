package com.petzel.dev.android.androidshowcase.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.petzel.dev.android.androidshowcase.repository.RedditClient
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {


    @Provides
    @Singleton
    fun provideOkhttp(networkFlipperPlugin: NetworkFlipperPlugin): OkHttpClient {
        val logging = (HttpLoggingInterceptor())
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        return (OkHttpClient.Builder()).apply {
            addInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
            addInterceptor(logging)
        }.build()
    }

    var API_BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}