package com.petzel.dev.android.androidshowcase

import android.app.Application
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.leakcanary.RecordLeakService
import com.facebook.soloader.SoLoader
import com.petzel.dev.android.androidshowcase.di.AppComponent
import com.petzel.dev.android.androidshowcase.di.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import es.dmoral.toasty.Toasty
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            // Leak detector
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.refWatcher(this)
                .listenerServiceClass(RecordLeakService::class.java)
                .buildAndInstall()
        }

        appComponent = DaggerAppComponent.builder().application(this).build()
        RxJavaPlugins.setErrorHandler { e ->
            appComponent.mainHandler().post {
                Timber.e(e)
                Toasty.error(this, e.message!!).show()
            }
        }
        initFlipper()
    }

    private fun initFlipper() {
        if (!FlipperUtils.shouldEnableFlipper(this)) {
            return
        }
        SoLoader.init(this, false)
        appComponent.flipperClient().start()
    }

}