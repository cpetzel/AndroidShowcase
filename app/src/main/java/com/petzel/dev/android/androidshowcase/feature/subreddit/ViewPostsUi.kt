package com.petzel.dev.android.androidshowcase.feature.subreddit

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.getActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

interface ViewPostsUi : Ui {
    fun showPosts(posts: List<Post>)
    fun postClicks(): Observable<Post>
    fun showSubredditSource(show: Boolean)
    fun refreshPosts(): Observable<Boolean>
}

open class ViewPostsUiImpl @Inject constructor(
    rootView: View,
    private val postAdapter: PostAdapter
) : AppUi(rootView), ViewPostsUi {

    val refreshSubject = PublishSubject.create<Boolean>()

    init {
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(rootView.context.getActivity())
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = postAdapter

        val swipeRefreshLayout = rootView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        swipeRefreshLayout.setOnRefreshListener {
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