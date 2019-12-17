package com.petzel.dev.android.androidshowcase.feature.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petzel.dev.android.androidshowcase.MainActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.uber.autodispose.ScopeProvider
import dagger.*
import timber.log.Timber
import javax.inject.Inject

class SelectSubredditFragment : BaseFragment() {

    @Inject
    lateinit var presenter: SelectSubredditPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")

        (activity!! as MainActivity).activityComponent!!.selectSubredditFactory().create(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_subreddit, container, false)
    }

    @Module
    abstract class SelectSubredditModule {

        @Binds
        abstract fun selectSubredditPresenter(impl: SelectSubredditPresenterImpl): SelectSubredditPresenter

        @Binds
        abstract fun selectSubredditUi(impl: SubredditSelectUiImpl): SubredditSelectUi

        @Module
        companion object {
            @PerFragment
            @Provides
            @JvmStatic
            fun scopeProvider(fragment: BaseFragment): ScopeProvider = fragment.scopeProvider
        }
    }

    @PerFragment
    @Subcomponent(
        modules = [SelectSubredditModule::class]
    )
    interface SelectSubredditFragmentComponent {
        @Subcomponent.Factory
        interface Factory {
            fun create(
                @BindsInstance fragment: BaseFragment
            ): SelectSubredditFragmentComponent
        }

        fun inject(fragment: SelectSubredditFragment)
    }
}

