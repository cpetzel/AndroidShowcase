package com.petzel.dev.android.androidshowcase.feature.select

import androidx.lifecycle.LifecycleObserver
import com.petzel.dev.android.androidshowcase.core.Navigator


class SelectSubredditPresenter(
    private val navigator: Navigator
) : LifecycleObserver {

    fun onSubredditSelected(subreddit: String) {
        navigator.goToViewSubreddit(subreddit)
    }
}
