package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.lifecycle.LifecycleOwner
import com.petzel.dev.android.androidshowcase.domain.Post


interface ViewSubredditController : LifecycleOwner {
    fun showProgress(show: Boolean)
    fun showPosts(posts: List<Post>)
    fun showError(error: String)
}