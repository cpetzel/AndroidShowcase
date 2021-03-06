package com.petzel.dev.android.androidshowcase.di

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseDriver
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseProvider
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class FlipperModule {

    @Singleton
    @Provides
    fun provideNetworkFlipperPlugin(): NetworkFlipperPlugin = NetworkFlipperPlugin()


    @Provides
    @Singleton
    fun provideFlipperClient(
        app: Application,
        networkFlipperPlugin: NetworkFlipperPlugin
    ): FlipperClient {

        return (AndroidFlipperClient.getInstance(app)).apply {
            addPlugin(InspectorFlipperPlugin(app, DescriptorMapping.withDefaults()))
            addPlugin(
                DatabasesFlipperPlugin(
                    SqliteDatabaseDriver(
                        app,
                        SqliteDatabaseProvider {
                            app.databaseList().filter {
                                it.contains("posts")
                            }.map { app.getDatabasePath(it) }
                        })
                )
            )
            addPlugin(LeakCanaryFlipperPlugin())
            addPlugin(SharedPreferencesFlipperPlugin(app))
            addPlugin(CrashReporterPlugin.getInstance())
            addPlugin(networkFlipperPlugin)
        }
    }

}