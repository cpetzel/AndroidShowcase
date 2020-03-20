package com.petzel.dev.android.androidshowcase.feature.managesubreddit.workflow

import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.domain.Subreddit
import com.petzel.dev.android.androidshowcase.repository.SubredditsRepository
import com.squareup.workflow.*
import com.squareup.workflow.rx2.asWorker
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

data class ManageSubredditsState(
    val items: List<Subreddit>
)

data class ManageSubredditRendering(
    val items: List<Subreddit>,
    val addSubreddit: (String) -> Unit,
    val deleteSubreddit: (Subreddit) -> Unit,
    val subredditTapped: (String) -> Unit
)

class ManageSubredditWorkflow @Inject constructor(
    val subredditsRepository: SubredditsRepository,
    val navigator: Navigator
) :
    StatefulWorkflow<Unit, ManageSubredditsState, Nothing, ManageSubredditRendering>() {

    override fun initialState(props: Unit, snapshot: Snapshot?): ManageSubredditsState =
        ManageSubredditsState(items = listOf())

    private fun onListUpdated(updatedItems: List<Subreddit>) = action {
        nextState = ManageSubredditsState(items = updatedItems)
    }

    override fun render(
        props: Unit,
        state: ManageSubredditsState,
        context: RenderContext<ManageSubredditsState, Nothing>
    ): ManageSubredditRendering {

        // listen for subreddit db updates, and update the state
        context.runningWorker(subredditsRepository.getAllSubreddits().asWorker()) {
            println("got list from worker $it")
            onListUpdated(it)
        }

        val addSubreddit = { name: String ->
            //cant use context worker here.. just put it into the db and the worker will pick it up
            subredditsRepository.addSubreddit(name).subscribeOn(Schedulers.io()).subscribe()
            println("add $name")
        }
        val deleteSubreddit = { subreddit: Subreddit ->
            subredditsRepository.delete(subreddit).subscribeOn(Schedulers.io()).subscribe()
            println("delete $subreddit")
        }
        val subredditTapped = { name: String ->
            println("tapped $name")
            navigator.goToViewSubreddit(name)
        }

        return ManageSubredditRendering(
            items = state.items,
            addSubreddit = addSubreddit,
            deleteSubreddit = deleteSubreddit,
            subredditTapped = subredditTapped
        )
    }

    override fun snapshotState(state: ManageSubredditsState): Snapshot = Snapshot.EMPTY
}
