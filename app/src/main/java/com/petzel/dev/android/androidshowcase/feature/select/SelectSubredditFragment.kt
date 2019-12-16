package com.petzel.dev.android.androidshowcase.feature.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import de.mateware.snacky.Snacky
import kotlinx.android.synthetic.main.fragment_select_subreddit.*
import timber.log.Timber

class SelectSubredditFragment : BaseFragment(), SelectSubredditController {

    private lateinit var presenter: SelectSubredditPresenter

    override fun showError(error: String) {
        handler.post { Snacky.builder().setActivity(activity!!).setText(error).error().show() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")

        // todo inject this
        presenter = SelectSubredditPresenter(
            navigator
        )
        setupForm()
    }

    private fun setupForm() {
        activity!!.subredditSelectButton.setOnClickListener {
            presenter.onSubredditSelected(subredditEditText.text.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_subreddit, container, false)
    }

}
