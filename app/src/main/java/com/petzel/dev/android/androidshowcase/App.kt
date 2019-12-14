package com.petzel.dev.android.androidshowcase

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initFlipper()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("HELLO")
    }


    private fun initFlipper() {
        SoLoader.init(this, false)
        if (!FlipperUtils.shouldEnableFlipper(this)) {
            return
        }
        val client = AndroidFlipperClient.getInstance(this)

        client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
        client.addPlugin(DatabasesFlipperPlugin(this))
        client.addPlugin(SharedPreferencesFlipperPlugin(this))
        client.addPlugin(CrashReporterPlugin.getInstance())

        val networkFlipperPlugin = NetworkFlipperPlugin()
        client.addPlugin(networkFlipperPlugin)
        client.start()
    }

}