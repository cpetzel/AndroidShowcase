package com.petzel.dev.android.androidshowcase.feature.subreddit

import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

interface ViewSubredditPresenter

class ViewSubredditPresenterImpl @Inject constructor(
    private val ui: ViewPostsUi,
    scopeProvider: ScopeProvider,
    postRepository: PostRepository,
    private val subreddit: String
) : ViewSubredditPresenter {
    init {
        Timber.d("constructing new presenter.. will grab from DB for subreddit $subreddit")

        postRepository.getSubredditPosts(subreddit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose {
                Timber.d("DAO unsubscribe")
            }
            .autoDisposable(scopeProvider)
            .subscribe {
                Timber.d("From DAO got ${it.size} posts")
                ui.showPosts(it)
            }

        postRepository.refreshPostsForSubreddit(subreddit)
            .doOnSubscribe {
                Timber.d("refreshing posts for $subreddit")
                ui.showProgress(true)
            }
            .doOnError {
                Timber.e(it)
                ui.snackError(it.message!!)
            }
            .onErrorComplete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                ui.showProgress(false)
            }
            .autoDisposable(scopeProvider)
            .subscribe()
    }
}
