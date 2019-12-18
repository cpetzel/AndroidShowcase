package com.petzel.dev.android.androidshowcase.di

import android.app.Application
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttp(networkFlipperPlugin: NetworkFlipperPlugin, app: Application): OkHttpClient {
        val logging = (HttpLoggingInterceptor())
            .apply { level = HttpLoggingInterceptor.Level.BASIC }


        return (OkHttpClient.Builder()).apply {
            addInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
            addInterceptor(logging)
            addInterceptor(ChuckInterceptor(app))
        }.build()
    }

    var API_BASE_URL = "https://reddit.com/"

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