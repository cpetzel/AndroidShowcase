package com.petzel.dev.android.androidshowcase.database

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

@Dao
interface PostDao {


    @Query("select * from databasepost ORDER BY created_at DESC")
    fun getPosts(): Observable<List<DatabasePost>>

    @Query("select * from databasepost WHERE subreddit COLLATE NOCASE = :subreddit ORDER BY created_at DESC")
    fun getPosts(subreddit: String): Observable<List<DatabasePost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<DatabasePost>)

    @Query("DELETE FROM databasepost WHERE subreddit COLLATE NOCASE = :subreddit")
    fun deletePostsForSubreddit(subreddit: String)

    @Query("select * from databasepost  WHERE id = :postId LIMIT 1")
    fun getPost(postId: String): Single<DatabasePost>
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


@Database(entities = [DatabasePost::class, DatabaseSubreddit::class], version = 8)
@TypeConverters(Converters::class)
abstract class PostsDatabase : RoomDatabase() {
    abstract val postDao: PostDao
    abstract val subredditDao: SubredditDao
}


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}