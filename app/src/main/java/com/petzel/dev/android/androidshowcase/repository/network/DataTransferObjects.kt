package com.petzel.dev.android.androidshowcase.repository.network

import com.petzel.dev.android.androidshowcase.database.DatabasePost
import com.petzel.dev.android.androidshowcase.domain.Post

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
        val url: String,
        val num_comments: Int,
        val created_utc: Int,
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
            permalink = it.data.permalink
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
            subreddit_id = it.data.subreddit_id
        )
    }
}