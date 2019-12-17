package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.domain.Post
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_view_subreddit.*
import javax.inject.Inject

interface ViewSubredditUi : Ui {
    fun showPosts(posts: List<Post>)
    fun postClicks(): Observable<Post>
}

class ViewSubredditUiImpl @Inject constructor(
    activity: FragmentActivity,
    private val postAdapter: PostAdapter
) : AppUi(activity), ViewSubredditUi {

    init {
        activity.recyclerView.layoutManager = LinearLayoutManager(activity)
        activity.recyclerView.itemAnimator = DefaultItemAnimator()
        activity.recyclerView.adapter = postAdapter
    }

    override fun showPosts(posts: List<Post>) {
        postAdapter.setItems(posts)
    }

    override fun postClicks() = postAdapter.postClicks
}