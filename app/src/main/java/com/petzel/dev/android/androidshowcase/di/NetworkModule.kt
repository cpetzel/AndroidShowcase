package com.petzel.dev.android.androidshowcase.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
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
    fun provideInterceptors(networkFlipperPlugin: NetworkFlipperPlugin): List<Interceptor> {
        val logging = (HttpLoggingInterceptor())
            .apply { level = HttpLoggingInterceptor.Level.BODY }

        return listOf(logging, FlipperOkhttpInterceptor(networkFlipperPlugin))
    }

    @Provides
    @Singleton
    fun provideOkhttp(interceptors: List<Interceptor>): OkHttpClient {
        return (OkHttpClient.Builder()).apply {
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}