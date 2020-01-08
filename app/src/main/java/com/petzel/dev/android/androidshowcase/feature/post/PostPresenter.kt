package com.petzel.dev.android.androidshowcase.feature.post

import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

interface PostPresenter

class PostPresenterImpl @Inject constructor(
    private val ui: PostUi,
    scopeProvider: ScopeProvider,
    postRepository: PostRepository,
    postId: String
) : PostPresenter {

    init {
        postRepository.getPost(postId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                ui.showPostDetails(it)
            }
            .autoDisposable(scopeProvider)
            .subscribe()
    }
}