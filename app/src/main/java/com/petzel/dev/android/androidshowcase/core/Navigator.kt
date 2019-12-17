package com.petzel.dev.android.androidshowcase.core

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.petzel.dev.android.androidshowcase.di.PerActivity
import com.petzel.dev.android.androidshowcase.feature.select.SelectSubredditFragmentDirections
import javax.inject.Inject

interface Navigator {
    fun goToViewSubreddit(subreddit: String)
}

@PerActivity
class NavigatorImpl @Inject constructor(private val navController: NavController) : Navigator {
    lateinit var fragment: Fragment

    override fun goToViewSubreddit(subreddit: String) {
        val action = SelectSubredditFragmentDirections.viewSubredditAction(subreddit)
        navController.navigate(action)
    }
}