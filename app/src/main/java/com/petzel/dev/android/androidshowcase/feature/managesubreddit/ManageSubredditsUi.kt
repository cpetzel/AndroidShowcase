package com.petzel.dev.android.androidshowcase.feature.managesubreddit

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.core.rx.clicksThrottle
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_manage_subreddits.*
import javax.inject.Inject


interface ManageSubredditsUi : Ui {

    fun showSubreddits(subreddits: List<Subreddit>)
    fun subredditsToAdd(): Observable<String>
    fun deleteSubreddits(): Observable<Subreddit>

    fun subredditClicks(): Observable<Subreddit>

}

@PerFragment
class ManageSubredditsUiImpl @Inject constructor(
    private val activity: FragmentActivity,
    private val subredditAdapter: SubredditAdapter
) : AppUi(activity), ManageSubredditsUi {

    init {
        activity.recyclerView.layoutManager = LinearLayoutManager(activity)
        activity.recyclerView.itemAnimator = DefaultItemAnimator()
        activity.recyclerView.adapter = subredditAdapter
    }

    override fun showSubreddits(subreddits: List<Subreddit>) {
        subredditAdapter.setItems(subreddits)
    }

    override fun deleteSubreddits(): Observable<Subreddit> =
        subredditAdapter.deleteSubredditClicks

    override fun subredditsToAdd(): Observable<String> =
        activity.manageSubredditAdd.clicksThrottle().map {
            activity.manageSubredditEditText.text.toString().toLowerCase().trim()
        }.doOnNext {
            activity.manageSubredditEditText.text.clear()
        }

    override fun subredditClicks(): Observable<Subreddit> = subredditAdapter.subredditClicks

}
