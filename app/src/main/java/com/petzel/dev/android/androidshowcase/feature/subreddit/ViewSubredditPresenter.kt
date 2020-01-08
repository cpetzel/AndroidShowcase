package com.petzel.dev.android.androidshowcase.feature.subreddit

import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
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
                Timber.d("From DAO got ${it.size} posts for subreddit $subreddit")
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

        ui.refreshPosts()
            .flatMapCompletable {
                postRepository.refreshPostsForSubreddit(subreddit)
                    .doOnSubscribe {
                        Timber.d("refreshing $subreddit")
                        ui.showProgress(true)
                    }
                    .timeout(10, TimeUnit.SECONDS)
                    .doOnError {
                        Timber.e(it)
                        ui.snackError(it.message!!)
                    }
                    .onErrorComplete()
                    .doOnComplete {
                        Timber.d("done refreshing $subreddit")
                        ui.showProgress(false)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe()
    }
}
