package com.petzel.dev.android.androidshowcase.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.petzel.dev.android.androidshowcase.core.rx.CustomScopeEvent
import com.petzel.dev.android.androidshowcase.core.rx.CustomScopeEvent.Companion.CORRESPONDING_EVENTS
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

/**
 * This class is used to manage the ScopeProvider for RxStreams...
 *
 * It is currently tied to the view lifecycle, because the NavController does not retain fragments. (it calls `replace`, instead of add on the transaction)
 *
 * Ideally, I can remove support for this, and have the scope live as long as the fragment is alive (where its view is retained)
 */

interface TitleProvider {
    val title: String?
}


abstract class BaseFragment : Fragment(),
    LifecycleScopeProvider<CustomScopeEvent> {

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
        Timber.tag(javaClass.simpleName).i("onCreate")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.tag(javaClass.simpleName).i("onActivityCreated")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag(javaClass.simpleName).i("onAttach")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(javaClass.simpleName).i("onStart")
    }

    override fun onStop() {
        Timber.tag(javaClass.simpleName).i("onStop")
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(javaClass.simpleName).i("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(javaClass.simpleName).i("onViewCreated")
        lifecycleEvents.onNext(CustomScopeEvent.ATTACH)
    }

    override fun onDestroyView() {
        Timber.tag(javaClass.simpleName).i("onDestroyView")
        lifecycleEvents.onNext(CustomScopeEvent.DETACH)
        super.onDestroyView()
    }


    override fun onDestroy() {
        Timber.tag(javaClass.simpleName).i("onDestroy")
        super.onDestroy()
    }


}