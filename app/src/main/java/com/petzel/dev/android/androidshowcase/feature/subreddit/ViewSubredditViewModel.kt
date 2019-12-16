package com.petzel.dev.android.androidshowcase.feature.subreddit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petzel.dev.android.androidshowcase.domain.Post

class ViewSubredditViewModel : ViewModel() {
    var isProgressVisible: MutableLiveData<Boolean> = MutableLiveData()
    var posts: MutableLiveData<List<Post>> = MutableLiveData()
}