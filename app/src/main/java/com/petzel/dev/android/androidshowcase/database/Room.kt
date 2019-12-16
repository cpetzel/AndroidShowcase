package com.petzel.dev.android.androidshowcase.database

import androidx.room.*
import io.reactivex.Observable

@Dao
interface PostDao {

    @Query("select * from databasepost")
    fun getPosts(): Observable<List<DatabasePost>>

    @Query("select * from databasepost WHERE subreddit  = :subreddit")
    fun getPosts(subreddit: String): Observable<List<DatabasePost>>

    // toto get post for single subreddit
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<DatabasePost>)
}

@Database(entities = [DatabasePost::class], version = 2)
abstract class PostsDatabase : RoomDatabase() {
    abstract val postDao: PostDao
}
