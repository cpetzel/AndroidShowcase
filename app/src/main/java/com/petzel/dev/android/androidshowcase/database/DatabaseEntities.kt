package com.petzel.dev.android.androidshowcase.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import java.util.*


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
    val permalink: String,
    val post_hint: String?,
    val selftext: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Date
)

@Entity
data class DatabaseSubreddit(
    @PrimaryKey val name: String
)

fun String.asSubredditDatabaseModel() = DatabaseSubreddit(name = this)


/**
 * Map DatabaseVideos to domain entities
 */
@JvmName("postToDomain")

fun List<DatabasePost>.asDomainModel(): List<Post> {
    return map {
        it.asDomainModel()
    }
}

fun DatabasePost.asDomainModel(): Post = Post(
    id = this.id,
    author = this.author,
    url = this.url,
    title = this.title,
    permalink = this.permalink,
    subreddit = this.subreddit,
    description = this.description,
    thumbnail = this.thumbnail,
    selftext = this.selftext,
    post_hint = this.post_hint,
    createdAt = this.createdAt
)

@JvmName("subredditToDomain")
fun List<DatabaseSubreddit>.asDomainModel(): List<Subreddit> {
    return map {
        Subreddit(
            name = it.name
        )
    }
}
