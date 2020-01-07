package com.petzel.dev.android.androidshowcase.repository

import com.petzel.dev.android.androidshowcase.repository.network.SubredditPostsContainer
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditApi {
    @GET("/r/{subreddit}.json")
    fun postsForSubreddit(
        @Path("subreddit") subreddit: String
    ): Single<SubredditPostsContainer>
}
