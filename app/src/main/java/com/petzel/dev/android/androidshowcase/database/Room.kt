package com.petzel.dev.android.androidshowcase.database

import androidx.room.*
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import io.reactivex.Observable

@Dao
interface PostDao {

    @Query("select * from databasepost")
    fun getPosts(): Observable<List<DatabasePost>>

    @Query("select * from databasepost WHERE subreddit COLLATE NOCASE = :subreddit")
    fun getPosts(subreddit: String): Observable<List<DatabasePost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<DatabasePost>)
}

@Dao
interface SubredditDao {

    @Query("select * from databasesubreddit")
    fun getSubreddits(): Observable<List<DatabaseSubreddit>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSubreddit(subreddit: DatabaseSubreddit)

    @Delete
    fun delete(subreddit: DatabaseSubreddit)
}


@Database(entities = [DatabasePost::class, DatabaseSubreddit::class], version = 3)
abstract class PostsDatabase : RoomDatabase() {
    abstract val postDao: PostDao
    abstract val subredditDao: SubredditDao
}
