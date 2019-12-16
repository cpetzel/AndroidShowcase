package com.petzel.dev.android.androidshowcase.feature.select

import androidx.lifecycle.LifecycleOwner

interface SelectSubredditController : LifecycleOwner {
    fun showError(error: String)
}