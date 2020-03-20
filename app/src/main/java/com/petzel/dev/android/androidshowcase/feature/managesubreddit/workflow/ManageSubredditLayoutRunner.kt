package com.petzel.dev.android.androidshowcase.feature.managesubreddit.workflow

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.SubredditAdapter
import com.petzel.dev.android.androidshowcase.getActivity
import com.squareup.workflow.ui.ContainerHints
import com.squareup.workflow.ui.LayoutRunner
import com.squareup.workflow.ui.ViewBinding

class ManageSubredditLayoutRunner(view: View) : LayoutRunner<ManageSubredditRendering> {

    private val addButton = view.findViewById<Button>(R.id.manageSubredditAdd)
    private val subredditInput = view.findViewById<EditText>(R.id.manageSubredditEditText)
    private val adapter = SubredditAdapter()

    init {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context.getActivity())
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }

    override fun showRendering(
        rendering: ManageSubredditRendering,
        containerHints: ContainerHints
    ) {
        adapter.setItems(rendering.items)
        adapter.onDeleteClickListener = rendering.deleteSubreddit
        adapter.onSubredditClickListener = rendering.subredditTapped
        addButton.setOnClickListener {
            rendering.addSubreddit(subredditInput.text.toString().toLowerCase().trim())
            subredditInput.text.clear()
        }
    }

    companion object : ViewBinding<ManageSubredditRendering> by LayoutRunner.bind(
        R.layout.fragment_manage_subreddits, ::ManageSubredditLayoutRunner
    )
}
