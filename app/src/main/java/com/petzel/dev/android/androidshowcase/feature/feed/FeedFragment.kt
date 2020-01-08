package com.petzel.dev.android.androidshowcase.feature.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petzel.dev.android.androidshowcase.MainActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.core.TitleProvider
import com.petzel.dev.android.androidshowcase.di.FragmentModule
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewPostsUi
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewPostsUiImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import timber.log.Timber
import javax.inject.Inject

class FeedFragment : BaseFragment(), TitleProvider {
    @Inject
    lateinit var presenter: FeedPresenter

    override val title: String?
        get() = "Feed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity!! as MainActivity).activityComponent!!.feedFactory()
            .create(this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_view_posts, container, false)
    }


    @Module
    abstract class FeedModule {

        @Binds
        abstract fun feedPresenter(impl: FeedPresenterImpl): FeedPresenter

        @Binds
        abstract fun feedUi(impl: ViewPostsUiImpl): ViewPostsUi
    }

    @PerFragment
    @Subcomponent(
        modules = [FeedModule::class, FragmentModule::class]
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