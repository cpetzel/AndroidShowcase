package com.petzel.dev.android.androidshowcase.feature.feed

import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.ManageSubredditsPresenter
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.ManageSubredditsUi
import com.petzel.dev.android.androidshowcase.feature.select.SelectSubredditPresenter
import com.petzel.dev.android.androidshowcase.feature.select.SubredditSelectUi
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewPostsUi
import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.petzel.dev.android.androidshowcase.repository.SubredditsRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface FeedPresenter

@PerFragment
class FeedPresenterImpl @Inject constructor(
    scopeProvider: ScopeProvider,
    ui: ViewPostsUi,
    postRepository: PostRepository,
    navigator: Navigator
) : FeedPresenter {
    init {

        ui.showSubredditSource(true)

        postRepository.posts
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe {
                Timber.d("From DAO (all) got ${it.size} posts")
                ui.showPosts(it)
            }

        ui.postClicks()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe {
                navigator.goToViewPost(it)
            }

        ui.refreshPosts()
            .flatMapCompletable {
                postRepository.refreshAll()
                    .doOnSubscribe {
                        Timber.d("refreshing all subreddits...")
                        ui.showProgress(true)
                    }
                    .timeout(20, TimeUnit.SECONDS)
                    .doOnError {
                        Timber.e(it)
                        ui.snackError(it.message!!)
                    }
                    .onErrorComplete()
                    .doOnComplete {
                        Timber.d("done refreshing all subreddits")
                        ui.showProgress(false)
                    }
            }

            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe()
    }
}
