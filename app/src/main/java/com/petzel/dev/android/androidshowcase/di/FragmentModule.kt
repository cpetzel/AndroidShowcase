package com.petzel.dev.android.androidshowcase.di

import android.view.View
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.uber.autodispose.ScopeProvider
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {
    @PerFragment
    @Provides
    fun scopeProvider(fragment: BaseFragment): ScopeProvider = fragment.scopeProvider

    @PerFragment
    @Provides
    fun rootViewProvider(fragment: BaseFragment): View = fragment.view!!
}