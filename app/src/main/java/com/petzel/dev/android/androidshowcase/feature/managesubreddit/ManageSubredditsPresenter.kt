package com.petzel.dev.android.androidshowcase.feature.managesubreddit

import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.repository.SubredditsRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


interface ManageSubredditsPresenter

@PerFragment
class ManageSubredditsPresenterImpl @Inject constructor(
    scopeProvider: ScopeProvider,
    manageSubredditsUi: ManageSubredditsUi,
    subredditsRepository: SubredditsRepository,
    navigator: Navigator
) : ManageSubredditsPresenter {
    init {

        subredditsRepository.getAllSubreddits()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose {
                Timber.d("subreddit DAO unsubscribe")
            }
            .autoDisposable(scopeProvider)
            .subscribe {
                Timber.d("From subreddit DAO got ${it.size} subreddits")
                manageSubredditsUi.showSubreddits(it)
            }

        manageSubredditsUi.deleteSubreddits()
            .observeOn(Schedulers.io())
            .autoDisposable(scopeProvider)
            .subscribe {
                subredditsRepository.delete(it)
            }


        manageSubredditsUi.subredditsToAdd()
            .observeOn(Schedulers.io())
            .autoDisposable(scopeProvider)
            .subscribe {
                subredditsRepository.addSubreddit(it)
            }

        manageSubredditsUi.subredditClicks()
            .autoDisposable(scopeProvider)
            .subscribe {
                navigator.goToViewSubreddit(it.name)
            }

    }
}
