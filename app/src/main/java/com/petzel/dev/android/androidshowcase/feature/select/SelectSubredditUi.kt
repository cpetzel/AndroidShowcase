package com.petzel.dev.android.androidshowcase.feature.select

import androidx.fragment.app.FragmentActivity
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.core.rx.clicksThrottle
import com.petzel.dev.android.androidshowcase.di.PerFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_select_subreddit.*
import javax.inject.Inject

interface SubredditSelectUi : Ui {
    fun subredditSelectedClicks(): Observable<String>
}

@PerFragment
class SubredditSelectUiImpl @Inject constructor(
    private val activity: FragmentActivity
) : AppUi(activity), SubredditSelectUi {

    init {
        activity.toolbar.title = "TEST"
    }

    override fun subredditSelectedClicks(): Observable<String> =
        activity.subredditSelectButton.clicksThrottle().map {
            activity.subredditEditText.text.toString().trim()
        }
}
