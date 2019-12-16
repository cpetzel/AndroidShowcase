package com.petzel.dev.android.androidshowcase.feature.subreddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.domain.Post
import com.petzel.dev.android.androidshowcase.feature.select.SelectSubredditPresenter
import de.mateware.snacky.Snacky
import timber.log.Timber

private const val ARG_SUBREDDIT = "subreddit"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ViewSubredditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ViewSubredditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewSubredditFragment : BaseFragment(), ViewSubredditController {

    private lateinit var presenter: ViewSubredditPresenter
    private var subreddit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subreddit = it.getString(ARG_SUBREDDIT)
        }
        Timber.d("viewing subreddit fragment with $subreddit")
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")

        // todo inject this
        presenter = ViewSubredditPresenter(
            this,
            this,
//            ViewModelProviders.of(this).get(ViewSubredditViewModel::class.java),
            postRepository,
            subreddit!!
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_subreddit, container, false)
    }

    override fun showProgress(show: Boolean) {

    }

    override fun showPosts(posts: List<Post>) {
        handler.post {
            Snacky.builder().setActivity(activity!!).setText("will show ${posts.size}").info()
                .show()
        }
    }

    override fun showError(error: String) {
        handler.post { Snacky.builder().setActivity(activity!!).setText(error).error().show() }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param subreddit Parameter 1.
         * @return A new instance of fragment ViewSubredditFragment.
         */
        @JvmStatic
        fun newInstance(subreddit: String) =
            ViewSubredditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SUBREDDIT, subreddit)
                }
            }
    }
}
