package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.domain.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_view_feed.*
import javax.inject.Inject

interface ViewPostsUi : Ui {
    fun showPosts(posts: List<Post>)
    fun postClicks(): Observable<Post>
    fun showSubredditSource(show: Boolean)
    fun refreshPosts(): Observable<Boolean>
}

open class ViewPostsUiImpl @Inject constructor(
    activity: FragmentActivity,
    private val postAdapter: PostAdapter,
    recyclerView: RecyclerView
) : AppUi(activity), ViewPostsUi {

    val refreshSubject = PublishSubject.create<Boolean>()


    init {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = postAdapter

        activity.swipeRefresh.setOnRefreshListener {
            refreshSubject.onNext(true)
        }
    }

    override fun showSubredditSource(show: Boolean) =
        postAdapter.showSubredditSource(show)


    override fun showPosts(posts: List<Post>) {
        postAdapter.setItems(posts)
    }

    override fun refreshPosts(): Observable<Boolean> =
        refreshSubject.hide()

    override fun postClicks() = postAdapter.postClicks
}