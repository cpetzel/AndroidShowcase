package com.petzel.dev.android.androidshowcase.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.petzel.dev.android.androidshowcase.domain.Post


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */
/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class DatabasePost(
    @PrimaryKey
    val id: String,
    val url: String,
    val title: String,
    val description: String?,
    val author: String,
    val thumbnail: String,
    val subreddit: String,
    val subreddit_id: String,
    val permalink: String
)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<DatabasePost>.asDomainModel(): List<Post> {
    return map {
        Post(
            id = it.id,
            author = it.author,
            url = it.url,
            title = it.title,
            permalink = it.permalink,
            subreddit = it.subreddit,
            description = it.description,
            thumbnail = it.thumbnail
        )
    }
}
