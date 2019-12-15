package com.petzel.dev.android.androidshowcase

import android.app.Application
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.soloader.SoLoader
import com.petzel.dev.android.androidshowcase.di.AppComponent
import com.petzel.dev.android.androidshowcase.di.DaggerAppComponent
import com.petzel.dev.android.androidshowcase.repository.GitHubRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        appComponent.redditClient().reposForUser("cpetzel")
            .enqueue(object : Callback<List<GitHubRepo>> {
                override fun onFailure(call: Call<List<GitHubRepo>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<List<GitHubRepo>>,
                    response: Response<List<GitHubRepo>>
                ) {
                    Timber.d("got list $response")
                }
            })
    }

}