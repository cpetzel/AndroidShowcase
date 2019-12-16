package com.petzel.dev.android.androidshowcase.feature.select

import androidx.fragment.app.FragmentActivity
import com.petzel.dev.android.androidshowcase.core.rx.clicksThrottle
import com.petzel.dev.android.androidshowcase.di.PerFragment
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
    private val activity: FragmentActivity
) : SubredditSelectUi {

    override fun subredditSelectedClicks(): Observable<String> =
        activity.subredditSelectButton.clicksThrottle().map {
            activity.subredditEditText.text.toString().trim()
        }

    override fun showError(it: Throwable) {
        Timber.e(it)
        Snacky.builder().setActivity(activity).error().setText(it.message as String).show()
    }
}
