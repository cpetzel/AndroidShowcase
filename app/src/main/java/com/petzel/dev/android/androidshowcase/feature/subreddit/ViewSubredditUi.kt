package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.fragment.app.FragmentActivity
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.domain.Post
import de.mateware.snacky.Snacky
import javax.inject.Inject

interface ViewSubredditUi : Ui {
    fun showPosts(posts: List<Post>)
}

class ViewSubredditUiImpl @Inject constructor(val activity: FragmentActivity) : AppUi(activity),
    ViewSubredditUi {

    override fun showPosts(posts: List<Post>) {
        activity.runOnUiThread {
            Snacky.builder().setActivity(activity).setText("will show ${posts.size}").info()
                .show()
        }
    }
}