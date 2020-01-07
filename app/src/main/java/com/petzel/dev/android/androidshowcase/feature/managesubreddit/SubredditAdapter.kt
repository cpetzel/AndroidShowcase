package com.petzel.dev.android.androidshowcase.feature.managesubreddit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_reddit_post.view.*
import kotlinx.android.synthetic.main.item_subreddit.view.*
import javax.inject.Inject

@PerFragment
class SubredditAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val deleteSubredditClicks: Observable<Subreddit> by lazy { subredditDeleteClicks.hide() }
    private val subredditDeleteClicks = PublishSubject.create<Subreddit>()


    val subredditClicks: Observable<Subreddit> by lazy { subredditClicks.hide() }
    private val subredditClicksSubject = PublishSubject.create<Subreddit>()

    private val data = mutableListOf<Subreddit>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubredditListViewHolder {
        LayoutInflater.from(parent.context).also {
            val view = it.inflate(R.layout.item_subreddit, parent, false)
            return SubredditListViewHolder(view)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        with(holder.itemView) {
            subredditName.text = item.name
        }
    }

    fun setItems(subreddits: List<Subreddit>) {
        val diffResult =
            DiffUtil.calculateDiff(
                DiffCallback(
                    oldSubreddits = this.data,
                    newSubreddits = subreddits
                )
            )
        data.clear()
        data.addAll(subreddits)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SubredditListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.subredditDelete.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    subredditDeleteClicks.onNext(data[adapterPosition])
                }
            }
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    subredditClicksSubject.onNext(data[adapterPosition])
                }
            }
        }
    }

    private inner class DiffCallback(
        private val oldSubreddits: List<Subreddit>,
        private val newSubreddits: List<Subreddit>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldSubreddits[oldItemPosition].name == newSubreddits[newItemPosition].name
        }

        override fun getOldListSize(): Int = oldSubreddits.size

        override fun getNewListSize(): Int = newSubreddits.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldSubreddits[oldItemPosition]
            val new = newSubreddits[newItemPosition]
            return old.name == new.name
        }
    }
}