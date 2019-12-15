package com.petzel.dev.android.androidshowcase.feature.select

import androidx.appcompat.app.AppCompatActivity
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.rx.clicksThrottle
import de.mateware.snacky.Snacky
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_select_subreddit.*
import timber.log.Timber
import javax.inject.Inject

interface SubredditSelectUi {
    fun subredditSelectedClicks(): Observable<String>
    fun showError(it: Throwable)
}

@PerFragment
class SubredditSelectUiImpl @Inject constructor(
    private val activity: AppCompatActivity
) : SubredditSelectUi {

    override fun subredditSelectedClicks(): Observable<String> =
        activity.subredditSelectButton.clicksThrottle().map { activity.subredditEditText.text.toString() }

    override fun showError(it: Throwable) {
        Timber.e(it)
        Snacky.builder().setActivity(activity).error().setText(it.message as String).show()
    }
}