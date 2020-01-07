package com.petzel.dev.android.androidshowcase.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.domain.Subreddit


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

@Entity
data class DatabaseSubreddit(
    @PrimaryKey
    val name: String
)

fun String.asSubredditDatabaseModel() = DatabaseSubreddit(name = this)


/**
 * Map DatabaseVideos to domain entities
 */
@JvmName("postToDomain")

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

@JvmName("subredditToDomain")
fun List<DatabaseSubreddit>.asDomainModel(): List<Subreddit> {
    return map {
        Subreddit(
            name = it.name
        )
    }
}
