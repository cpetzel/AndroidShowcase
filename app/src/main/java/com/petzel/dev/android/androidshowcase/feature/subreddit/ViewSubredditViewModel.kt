package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.lifecycle.MutableLiveData
import com.petzel.dev.android.androidshowcase.core.rx.AutoDisposeViewModel
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.repository.PostRepository
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ViewSubredditViewModel(subreddit: String) : AutoDisposeViewModel() {

    @Inject
    lateinit var postRepository: PostRepository
    init {
        postRepository.getSubredditPosts(subreddit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(this)
            .subscribe {
                Timber.d("From DAO got ${it.size} posts")
                //posts.value = it
            }

    }

//    var isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
//    var posts: MutableLiveData<List<Post>> = MutableLiveData()
}