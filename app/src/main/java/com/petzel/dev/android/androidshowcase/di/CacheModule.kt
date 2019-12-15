package com.petzel.dev.android.androidshowcase.di

import android.app.Application
import androidx.room.Room
import com.petzel.dev.android.androidshowcase.database.PostsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun providesPostsDatabase(app: Application): PostsDatabase {
        return Room.databaseBuilder(app, PostsDatabase::class.java, "posts").fallbackToDestructiveMigration() .build()
    }
}