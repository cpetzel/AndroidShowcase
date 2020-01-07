package com.petzel.dev.android.androidshowcase.repository

import com.petzel.dev.android.androidshowcase.database.PostsDatabase
import com.petzel.dev.android.androidshowcase.database.asDomainModel
import com.petzel.dev.android.androidshowcase.database.asSubredditDatabaseModel
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class SubredditsRepository @Inject constructor(
    private val database: PostsDatabase
) {

    fun getAllSubreddits(): Observable<List<Subreddit>> {
        return database.subredditDao.getSubreddits()
            .map { it.asDomainModel() }
            .doOnNext {
                Timber.d("SubredditRepo DAO now have ${it.size} items")
            }
    }

    fun addSubreddit(name: String) =
        database.subredditDao.insertSubreddit(name.asSubredditDatabaseModel())

    //TODO how do I convert between domain model and database models?
    fun delete(subreddit: Subreddit) {
        Timber.d("will delete subreddit with name == $subreddit")
        database.subredditDao.delete(subreddit.name.asSubredditDatabaseModel())
    }

}
