package com.petzel.dev.android.androidshowcase

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.soloader.SoLoader
import com.petzel.dev.android.androidshowcase.di.AppComponent
import com.petzel.dev.android.androidshowcase.di.DaggerAppComponent
import es.dmoral.toasty.Toasty
import io.reactivex.plugins.RxJavaPlugins
import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.LeakCanary
import leakcanary.OnHeapAnalyzedListener
import shark.HeapAnalysis
import timber.log.Timber

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            LeakCanary.config = LeakCanary.config.copy(
                onHeapAnalyzedListener = FlipperAnalyzer()
            )
        }

        appComponent = DaggerAppComponent.factory().create(this)

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

    inner class FlipperAnalyzer : OnHeapAnalyzedListener {

        val defaultListener = DefaultOnHeapAnalyzedListener.create()

        override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {

            // Delegate to default behavior (notification and saving result)
            defaultListener.onHeapAnalyzed(heapAnalysis)

            val client = AndroidFlipperClient.getInstance(this@App)

            if (client != null) {
                val plugin = client.getPlugin<LeakCanaryFlipperPlugin>("LeakCanary")
                plugin?.reportLeak(heapAnalysis.toString())
            }
        }
    }


}