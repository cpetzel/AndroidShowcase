package com.petzel.dev.android.androidshowcase.feature.managesubreddit

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.core.rx.clicksThrottle
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import com.petzel.dev.android.androidshowcase.getActivity
import io.reactivex.Observable
import javax.inject.Inject


interface ManageSubredditsUi : Ui {
    fun showSubreddits(subreddits: List<Subreddit>)
    fun subredditsToAdd(): Observable<String>
    fun deleteSubreddits(): Observable<Subreddit>
    fun subredditClicks(): Observable<Subreddit>
}

@PerFragment
class ManageSubredditsUiImpl @Inject constructor(
    rootView: View,
    private val drawerLayout: DrawerLayout,
    private val subredditAdapter: SubredditAdapter
) : AppUi(rootView), ManageSubredditsUi {

    private val addButton = rootView.findViewById<Button>(R.id.manageSubredditAdd)
    private val subredditInput = rootView.findViewById<EditText>(R.id.manageSubredditEditText)

    init {
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(rootView.context.getActivity())
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = subredditAdapter
    }

    override fun showSubreddits(subreddits: List<Subreddit>) {
        subredditAdapter.setItems(subreddits)
    }

    override fun deleteSubreddits(): Observable<Subreddit> =
        subredditAdapter.deleteSubredditClicks

    override fun subredditsToAdd(): Observable<String> =
        addButton.clicksThrottle().map {
            subredditInput.text.toString().toLowerCase().trim()
        }.doOnNext {
            subredditInput.text.clear()
        }

    override fun subredditClicks(): Observable<Subreddit> =
        subredditAdapter.subredditClicks.doOnNext {
            drawerLayout.closeDrawer(Gravity.START)
        }
}
