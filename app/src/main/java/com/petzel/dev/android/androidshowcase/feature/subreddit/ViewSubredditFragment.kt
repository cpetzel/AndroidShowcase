package com.petzel.dev.android.androidshowcase.feature.subreddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.petzel.dev.android.androidshowcase.MainActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.core.TitleProvider
import com.petzel.dev.android.androidshowcase.di.FragmentModule
import com.petzel.dev.android.androidshowcase.di.PerFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
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
class ViewSubredditFragment : BaseFragment(), TitleProvider {

    @Inject
    lateinit var presenter: ViewSubredditPresenter

    private var subreddit: String? = null

    override val title: String?
        get() = subreddit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subreddit = it.getString(ARG_SUBREDDIT)
        }
        Timber.d("onCreate viewing subreddit fragment with $subreddit")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!! as MainActivity).activityComponent!!.viewSubredditFactory()
            .create(this, subreddit!!).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_view_posts, container, false)
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

    }

    @PerFragment
    @Subcomponent(
        modules = [ViewSubredditModule::class, FragmentModule::class]
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
