package com.petzel.dev.android.androidshowcase.feature.select

import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


interface SelectSubredditPresenter

@PerFragment
class SelectSubredditPresenterImpl @Inject constructor(
    scope: ScopeProvider,
    selectSubredditSelectUi: SubredditSelectUi,
    navigator: Navigator
) : SelectSubredditPresenter {
    init {


        selectSubredditSelectUi.subredditSelectedClicks()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scope)
            .subscribe {
                navigator.goToViewSubreddit(it)
            }



    }
}
