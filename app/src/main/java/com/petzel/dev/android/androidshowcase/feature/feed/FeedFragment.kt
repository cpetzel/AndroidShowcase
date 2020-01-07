package com.petzel.dev.android.androidshowcase.feature.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petzel.dev.android.androidshowcase.MainActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.*
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.ManageSubredditsPresenter
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.ManageSubredditsPresenterImpl
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewPostsUi
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewPostsUiImpl
import com.uber.autodispose.ScopeProvider
import dagger.*
import kotlinx.android.synthetic.main.fragment_view_feed.*
import timber.log.Timber
import javax.inject.Inject

class FeedFragment : BaseFragment() {
    @Inject
    lateinit var presenter: FeedPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")
        (activity!! as MainActivity).activityComponent!!.feedFactory()
            .create(this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_feed, container, false)
    }


    @Module
    abstract class FeedModule {

        @Binds
        abstract fun feedPresenter(impl: FeedPresenterImpl): FeedPresenter


        @Binds
        abstract fun feedUi(impl: ViewPostsUiImpl): ViewPostsUi

        @Module
        companion object {
            @PerFragment
            @Provides
            @JvmStatic
            fun scopeProvider(fragment: BaseFragment): ScopeProvider = fragment.scopeProvider

            @PerFragment
            @Provides
            @JvmStatic
            fun recyclerViewProvider(fragment: BaseFragment): RecyclerView = fragment.activity!!.feedRecyclerView
        }
    }

    @PerFragment
    @Subcomponent(
        modules = [FeedModule::class]
    )
    interface FeedFragmentComponent {
        @Subcomponent.Factory
        interface Factory {
            fun create(
                @BindsInstance fragment: BaseFragment
            ): FeedFragmentComponent
        }

        fun inject(fragment: FeedFragment)
    }


}