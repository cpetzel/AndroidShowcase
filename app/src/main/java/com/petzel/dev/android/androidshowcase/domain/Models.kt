package com.petzel.dev.android.androidshowcase.domain

import java.util.*


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * Post represents a subreddit post.
 */
data class Post(
    val id: String,
    val title: String,
    val subreddit: String,
    val description: String?,
    val permalink: String,
    val url: String,
    val author: String,
    val thumbnail: String,
    val createdAt: Date
) {

    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
//    val shortDescription: String
//        get() = description.smartTruncate(200)
}


/**
 * Represents a Subreddit
 */
data class Subreddit(
    val name: String
) {
    val url: String
        get() = "www.reddit.com/r/$name"
}
