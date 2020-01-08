package com.petzel.dev.android.androidshowcase.feature.subreddit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.domain.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_reddit_post.view.*
import javax.inject.Inject

@PerFragment
class PostAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val postClicks: Observable<Post> by lazy { rowClicksSubject.hide() }
    private val rowClicksSubject = PublishSubject.create<Post>()
    private val data = mutableListOf<Post>()

    private var showSubredditSource = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        LayoutInflater.from(parent.context).also {
            val view = it.inflate(R.layout.item_reddit_post, parent, false)
            return PostListViewHolder(view)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        with(holder.itemView) {

            if (item.thumbnail.startsWith("http")) {
                Glide.with(this).load(item.thumbnail).into(postImage)
            }
            postTitle.text = item.title
            postDescription.text = item.description
            postSubreddit.text = "/r/${item.subreddit}"
            postSubreddit.visibility = if (showSubredditSource) View.VISIBLE else View.INVISIBLE
        }
    }

    fun showSubredditSource(show: Boolean) {
        showSubredditSource = show
        notifyDataSetChanged()
    }

    fun setItems(posts: List<Post>) {
        val diffResult =
            DiffUtil.calculateDiff(DiffCallback(oldPosts = this.data, newPosts = posts))
        data.clear()
        data.addAll(posts)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PostListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    rowClicksSubject.onNext(data[adapterPosition])
                }
            }
        }
    }

    private inner class DiffCallback(
        private val oldPosts: List<Post>,
        private val newPosts: List<Post>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition].id == newPosts[newItemPosition].id
        }

        override fun getOldListSize(): Int = oldPosts.size

        override fun getNewListSize(): Int = newPosts.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldPosts[oldItemPosition]
            val new = newPosts[newItemPosition]
            return old.thumbnail == new.thumbnail
                    && old.description == new.description
        }
    }
}