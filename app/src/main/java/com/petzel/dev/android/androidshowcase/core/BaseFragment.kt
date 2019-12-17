package com.petzel.dev.android.androidshowcase.core

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.petzel.dev.android.androidshowcase.core.rx.CustomScopeEvent
import com.petzel.dev.android.androidshowcase.core.rx.CustomScopeEvent.Companion.CORRESPONDING_EVENTS
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseFragment : Fragment(), LifecycleScopeProvider<CustomScopeEvent> {

    val scopeProvider: LifecycleScopeProvider<CustomScopeEvent> by lazy { this }

    private val lifecycleEvents = BehaviorSubject.create<CustomScopeEvent>()
    override fun lifecycle(): Observable<CustomScopeEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<CustomScopeEvent> {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): CustomScopeEvent? {
        return lifecycleEvents.value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleEvents.onNext(CustomScopeEvent.ATTACH)
    }

    override fun onDestroy() {
        lifecycleEvents.onNext(CustomScopeEvent.DETACH)
        super.onDestroy()
    }
}