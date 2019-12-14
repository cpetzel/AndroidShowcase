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
import com.petzel.dev.android.androidshowcase.di.AppComponent
import com.petzel.dev.android.androidshowcase.di.DaggerAppComponent
import timber.log.Timber


class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.d("HELLO")

        appComponent = DaggerAppComponent.builder().application(this).build()
        initFlipper()
    }


    private fun initFlipper() {
        SoLoader.init(this, false)
        if (!FlipperUtils.shouldEnableFlipper(this)) {
            return
        }

        appComponent.flipperClient().start()

//        client.start()


    }

}