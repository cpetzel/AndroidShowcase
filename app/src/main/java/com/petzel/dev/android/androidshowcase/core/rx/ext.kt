package com.petzel.dev.android.androidshowcase.core.rx

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun View.clicksThrottle(delayMillis: Long = 500): Observable<Unit> = clicks()
    .throttleFirst(delayMillis, TimeUnit.MILLISECONDS)
    .observeOn(AndroidSchedulers.mainThread())