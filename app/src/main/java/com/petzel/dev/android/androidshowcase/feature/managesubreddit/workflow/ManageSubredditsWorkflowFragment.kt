package com.petzel.dev.android.androidshowcase.feature.managesubreddit.workflow

import com.petzel.dev.android.androidshowcase.MainActivity
import com.squareup.workflow.diagnostic.SimpleLoggingDiagnosticListener
import com.squareup.workflow.ui.ContainerHints
import com.squareup.workflow.ui.ViewRegistry
import com.squareup.workflow.ui.WorkflowFragment
import com.squareup.workflow.ui.WorkflowRunner
import javax.inject.Inject

class ManageSubredditsWorkflowFragment : WorkflowFragment<Unit, Nothing>() {

    override val containerHints = ContainerHints(ViewRegistry(ManageSubredditLayoutRunner))

    @Inject
    lateinit var workflow: ManageSubredditWorkflow

    override fun onCreateWorkflow(): WorkflowRunner.Config<Unit, Nothing> {
        (activity!! as MainActivity).activityComponent!!.inject(this)
        return WorkflowRunner.Config(
            workflow,
            diagnosticListener = SimpleLoggingDiagnosticListener()
        )
    }
}
