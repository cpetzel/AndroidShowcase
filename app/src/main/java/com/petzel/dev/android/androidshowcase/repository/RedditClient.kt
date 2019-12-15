package com.petzel.dev.android.androidshowcase.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RedditClient {
    @GET("/users/{user}/repos")
    fun reposForUser(
        @Path("user") user: String
    ): Call<List<GitHubRepo>>
}

data class GitHubRepo(val id: Int, val name: String)