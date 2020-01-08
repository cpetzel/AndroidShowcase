package com.petzel.dev.android.androidshowcase.feature.post

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.petzel.dev.android.androidshowcase.AppUi
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.Ui
import com.petzel.dev.android.androidshowcase.domain.Post
import io.reactivex.Observable
import javax.inject.Inject

interface PostUi : Ui {
    fun imageClicks(): Observable<String>
    fun showPostDetails(post: Post)
}

class PostUiImpl @Inject constructor(rootView: View) : AppUi(rootView),
    PostUi {

    private val image = rootView.findViewById<ImageView>(R.id.image)
    private val title = rootView.findViewById<TextView>(R.id.title)
    private val selfText = rootView.findViewById<TextView>(R.id.selfText)

    override fun showPostDetails(post: Post) {

        if ("image" == post.post_hint) {
            image.visibility = View.VISIBLE

            //TODO show loading indicator
            Glide.with(image).load(post.url).into(image)
        } else if ("link" == post.post_hint && post.url.contains("gif")) {
            image.visibility = View.VISIBLE

            //TODO show loading indicator
            Glide.with(image).load(post.url).into(image)
        }else{
            image.visibility = View.GONE
        }

        selfText.text = post.selftext
        title.text = post.title

    }

    override fun imageClicks(): Observable<String> {
        return Observable.just("hi")
    }

}