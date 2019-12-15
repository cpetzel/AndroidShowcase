package com.petzel.dev.android.androidshowcase.feature.select

import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject


interface SelectSubredditPresenter {
    fun onCreate()
}

@PerFragment
class SelectSubredditPresenterImpl @Inject constructor(
    private val scope: ScopeProvider,
    private val postRepository: PostRepository,
    private val selectSubredditSelectUi: SubredditSelectUi
) : SelectSubredditPresenter {
    override fun onCreate() {

        /*  postRepository.refreshPostsForSubreddit("nsfw")
              .doOnError{
                  selectSubredditSelectUi.showError(it)
              }
              .onErrorComplete()
              .autoDisposable(scope)
              .subscribe()*/

        selectSubredditSelectUi.subredditSelectedClicks()
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scope)
            .subscribe { Timber.d("will load subreddit $it") }

        postRepository.posts
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scope)
            .subscribe { Timber.d("received new posts with size ${it.size}") }
    }


}