package com.petzel.dev.android.androidshowcase.feature.subreddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.MainActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.uber.autodispose.ScopeProvider
import dagger.*
import kotlinx.android.synthetic.main.fragment_view_subreddit.*
import timber.log.Timber
import javax.inject.Inject

private const val ARG_SUBREDDIT = "subreddit"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ViewSubredditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ViewSubredditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewSubredditFragment : BaseFragment() {

    @Inject
    lateinit var presenter: ViewSubredditPresenter

    private var subreddit: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subreddit = it.getString(ARG_SUBREDDIT)
        }
        Timber.d("onCreate viewing subreddit fragment with $subreddit")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")
        (activity!! as MainActivity).activityComponent!!.viewSubredditFactory()
            .create(this, subreddit!!).inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_subreddit, container, false)
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

    @Module
    abstract class ViewSubredditModule {

        @Binds
        abstract fun viewSubredditPresenter(impl: ViewSubredditPresenterImpl): ViewSubredditPresenter

        @Binds
        abstract fun viewSubredditUi(impl: ViewPostsUiImpl): ViewPostsUi

        @Module
        companion object {
            @PerFragment
            @Provides
            @JvmStatic
            fun scopeProvider(fragment: BaseFragment): ScopeProvider = fragment.scopeProvider

            @PerFragment
            @Provides
            @JvmStatic
            fun recyclerViewProvider(fragment: BaseFragment): RecyclerView =
                fragment.activity!!.viewSubredditRecyclerView
        }
    }

    @PerFragment
    @Subcomponent(
        modules = [ViewSubredditModule::class]
    )
    interface ViewSubredditFragmentComponent {
        @Subcomponent.Factory
        interface Factory {
            fun create(
                @BindsInstance fragment: BaseFragment,
                @BindsInstance subreddit: String
            ): ViewSubredditFragmentComponent
        }

        fun inject(fragment: ViewSubredditFragment)
    }
}
