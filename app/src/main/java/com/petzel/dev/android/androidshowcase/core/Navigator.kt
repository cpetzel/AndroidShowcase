package com.petzel.dev.android.androidshowcase.core

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.petzel.dev.android.androidshowcase.feature.select.SelectSubredditFragmentDirections
import timber.log.Timber

interface Navigator {
    fun setCurrentFragment(fragment: Fragment)

    fun goToViewSubreddit(subreddit: String)
}

class NavigatorImpl : Navigator {
    lateinit var fragment: Fragment

    override fun setCurrentFragment(fragment: Fragment) {
        Timber.d("Setting current fragment to ${fragment.id}")
        this.fragment = fragment
    }

    override fun goToViewSubreddit(subreddit: String) {
        val action = SelectSubredditFragmentDirections.viewSubredditAction(subreddit)
        fragment.findNavController().navigate(action)
    }
}