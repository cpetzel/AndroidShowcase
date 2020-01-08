package com.petzel.dev.android.androidshowcase.core

import androidx.fragment.app.FragmentActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.di.PerActivity
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewSubredditFragment
import javax.inject.Inject

interface Navigator {
    fun goToViewSubreddit(subreddit: String)
    fun goToViewPost(it: Post)
}

@PerActivity
class NavigatorImpl @Inject constructor(private val fragmentActivity: FragmentActivity) :
    Navigator {

    override fun goToViewSubreddit(subreddit: String) {
        fragmentActivity.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, ViewSubredditFragment.newInstance(subreddit), "VIEW")
            .addToBackStack("FEED")
            .commit()
    }

    override fun goToViewPost(it: Post) {

    }
}