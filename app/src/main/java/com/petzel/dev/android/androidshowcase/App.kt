package com.petzel.dev.android.androidshowcase

import android.app.Application
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.soloader.SoLoader
import com.petzel.dev.android.androidshowcase.di.AppComponent
import com.petzel.dev.android.androidshowcase.di.DaggerAppComponent
import es.dmoral.toasty.Toasty
import io.reactivex.plugins.RxJavaPlugins
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
        RxJavaPlugins.setErrorHandler { e ->
            appComponent.mainHandler().post {
                Timber.e(e)
                Toasty.error(this, e.message!!).show()
            }
        }
        initFlipper()

    }


    private fun initFlipper() {
        SoLoader.init(this, false)
        if (!FlipperUtils.shouldEnableFlipper(this)) {
            return
        }

        appComponent.flipperClient().start()
    }


}