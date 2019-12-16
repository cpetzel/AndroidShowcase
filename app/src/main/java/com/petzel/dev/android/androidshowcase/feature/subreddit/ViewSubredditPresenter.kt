package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


class ViewSubredditPresenter(
    private val controller: ViewSubredditController,
    private val viewModel: ViewSubredditViewModel,
    scopeProvider: ScopeProvider,
    private val navigator: Navigator,
    private val postRepository: PostRepository
) : LifecycleObserver {

    private val subredditSubject = PublishSubject.create<String>()

    init {
        Timber.d("constructing new presenter")
        viewModel.isProgressVisible.observe(controller, Observer<Boolean> { isProgressVisible ->
            controller.showProgress(isProgressVisible ?: false)
        })

        /* viewModel.posts.observe(controller, Observer<List<Post>> { posts ->
             controller.showPosts(posts)
         })*/

        subredditSubject.switchMap {
            Timber.d("will grab from DB for subreddit $it")

            postRepository.getSubredditPosts(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
            .autoDisposable(scopeProvider)
            .subscribe {
                Timber.d("subject switchMap got ${it.size} posts")
                controller.showPosts(it)
            }
    }

    fun loadSubreddit(subreddit: String) {
        subredditSubject.onNext(subreddit)

        postRepository.refreshPostsForSubreddit(subreddit)
            .doOnSubscribe {
                Timber.d("refreshing posts for ${subreddit}")
                viewModel.isProgressVisible.value = true
            }
            .doOnError {
                controller.showError(it.message!!)
            }
            .onErrorComplete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                viewModel.isProgressVisible.value = false
            }
            .subscribe()
    }
}