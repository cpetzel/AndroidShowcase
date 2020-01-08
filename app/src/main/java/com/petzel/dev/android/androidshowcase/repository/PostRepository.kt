package com.petzel.dev.android.androidshowcase.repository

import com.petzel.dev.android.androidshowcase.database.PostsDatabase
import com.petzel.dev.android.androidshowcase.database.asDomainModel
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.repository.network.asDatabaseModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val database: PostsDatabase,
    private val redditApi: RedditApi
) {

    val posts: Observable<List<Post>> = database.postDao.getPosts()
        .map { it.asDomainModel() }
        .doOnNext {
            Timber.d("PostRepository DAO now have ${it.size} items")
        }

    fun getSubredditPosts(subreddit: String): Observable<List<Post>> {
        return database.postDao.getPosts(subreddit)
            .map { it.asDomainModel() }
            .doOnNext {
                Timber.d("PostRepository DAO now have ${it.size} items")
            }
    }

    fun refreshPostsForSubreddit(subreddit: String): Completable {
        Timber.d("refreshPostsForSubreddit")

        return redditApi.postsForSubreddit(subreddit)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                Timber.d("refreshPostsForSubreddit will refresh posts for subreddit $subreddit")
            }
            .doOnSuccess { posts ->
                Timber.d("refreshPostsForSubreddit retrieved ${posts.data.children.size} posts for $subreddit... will persist")
                database.postDao.insertAll(posts.asDatabaseModel())
                Timber.d("refreshPostsForSubreddit successfully persisted")
            }
            .ignoreElement()

    }


    fun refreshAll(): Completable {
        Timber.d("refreshing ALL subreddits")

        return database.subredditDao.getSubreddits()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Timber.d("refreshAll retrieved ${it.size} subreddits to refresh")
            }
            .firstOrError()
            .flatMapCompletable {
                Completable.merge(it.map { subreddit ->
                    refreshPostsForSubreddit(subreddit.name)
                })
            }
            .doOnComplete {
                Timber.d("done refreshing ALL subreddits")
            }
    }

    fun getPost(postId: String): Single<Post> {
        Timber.d("getPost $postId")

        return database.postDao.getPost(postId)
            .subscribeOn(Schedulers.io())
            .map { it.asDomainModel() }

    }


}

