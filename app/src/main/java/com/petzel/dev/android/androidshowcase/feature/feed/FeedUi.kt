package com.petzel.dev.android.androidshowcase.feature.feed

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.feature.subreddit.PostAdapter
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewPostsUiImpl
import io.reactivex.Observable
import javax.inject.Inject


@PerFragment
class FeedUiImpl @Inject constructor(
    private val activity: FragmentActivity,
    private val postAdapter: PostAdapter,
    recyclerView: RecyclerView
) : ViewPostsUiImpl(activity, postAdapter, recyclerView) {


    override fun showSubredditSource(show: Boolean) {
        postAdapter.showSubredditSource(show)
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = postAdapter
    }

    override fun refreshPosts(): Observable<Boolean> =
        refreshSubject.hide()

    override fun showPosts(posts: List<Post>) {
        postAdapter.setItems(posts)
    }

    override fun postClicks() = postAdapter.postClicks

}
