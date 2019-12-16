package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.lifecycle.LifecycleObserver
import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ViewSubredditPresenter(
    private val controller: ViewSubredditController,
    scopeProvider: ScopeProvider,
    postRepository: PostRepository,
    private val subreddit: String
) : LifecycleObserver {

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
                controller.showPosts(it)
            }

        postRepository.refreshPostsForSubreddit(subreddit)
            .doOnSubscribe {
                Timber.d("refreshing posts for $subreddit")
//                viewModel.isProgressVisible.value = true
                controller.showProgress(true)
            }
            .doOnError {
                controller.showError(it.message!!)
            }
            .onErrorComplete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                //                viewModel.isProgressVisible.value = false
                controller.showProgress(false)
            }
            .subscribe()
    }
}