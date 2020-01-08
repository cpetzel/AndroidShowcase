package com.petzel.dev.android.androidshowcase.repository.network

import com.petzel.dev.android.androidshowcase.database.DatabasePost
import com.petzel.dev.android.androidshowcase.domain.Post
import java.util.*

data class SubredditPostsContainer(
    val kind: String, // Listing
    val data: SubredditListingData
) {
    data class SubredditListingData(
        val modhash: String,
        val children: List<WirePost>
    )
}


data class WirePost(
    val kind: String,
    val data: Data
) {
    data class Data(
        val id: String,
        val title: String,
        val description: String?,
        val author: String,
        val subreddit: String,
        val subreddit_id: String,
        val thumbnail: String,
        val permalink: String,
        val selftext: String,
        val post_hint: String?,
        val url: String,
        val num_comments: Int,
        val created_utc: Long,
        val is_video: Boolean = false
    )
}

fun SubredditPostsContainer.asDomainModel(): List<Post> {
    return data.children.map {
        Post(
            title = it.data.title,
            description = it.data.description,
            id = it.data.id,
            author = it.data.author,
            subreddit = it.data.subreddit,
            url = it.data.url,
            thumbnail = it.data.thumbnail,
            permalink = it.data.permalink,
            selftext = it.data.selftext,
            post_hint = it.data.post_hint,
            createdAt = Date(it.data.created_utc)
        )
    }
}

fun SubredditPostsContainer.asDatabaseModel(): List<DatabasePost> {
    return data.children.map {
        DatabasePost(
            title = it.data.title,
            description = it.data.description,
            id = it.data.id,
            author = it.data.author,
            subreddit = it.data.subreddit,
            url = it.data.url,
            thumbnail = it.data.thumbnail,
            permalink = it.data.permalink,
            post_hint = it.data.post_hint,
            selftext = it.data.selftext,
            subreddit_id = it.data.subreddit_id,
            createdAt = Date(it.data.created_utc)
        )
    }
}