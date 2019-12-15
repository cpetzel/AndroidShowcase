package com.petzel.dev.android.androidshowcase.repository

import com.petzel.dev.android.androidshowcase.database.PostsDatabase
import com.petzel.dev.android.androidshowcase.database.asDomainModel
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.repository.network.asDatabaseModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val database: PostsDatabase,
    private val redditApi: RedditApi
) {

    val posts: Observable<List<Post>> = database.postDao.getPosts().map { it.asDomainModel() }

    fun refreshPostsForSubreddit(subreddit: String): Completable {
        Timber.d("refreshPostsForSubreddit")

        return redditApi.postsForSubreddit(subreddit).subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Timber.d("refreshPostsForSubreddit will refresh posts for subreddit $subreddit")
            }
            .doOnSuccess { posts ->
                Timber.d("refreshPostsForSubreddit retrieved ${posts.data.children.size} posts... will persist")
                database.postDao.insertAll(posts.asDatabaseModel())
                Timber.d("refreshPostsForSubreddit successfully persisted")
            }
            .subscribeOn(Schedulers.io())
            .toCompletable()

    }

}
