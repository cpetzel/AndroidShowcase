package com.petzel.dev.android.androidshowcase.feature.managesubreddit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petzel.dev.android.androidshowcase.MainActivity
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import com.petzel.dev.android.androidshowcase.di.FragmentModule
import com.petzel.dev.android.androidshowcase.di.PerFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import timber.log.Timber
import javax.inject.Inject

class ManageSubredditsFragment : BaseFragment() {
    @Inject
    lateinit var presenter: ManageSubredditsPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")
        (activity!! as MainActivity).activityComponent!!.manageSubredditsFactory()
            .create(this).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_subreddits, container, false)
    }

    @Module
    abstract class ManageSubredditsModule {

        @Binds
        abstract fun manageSubredditPresenter(impl: ManageSubredditsPresenterImpl): ManageSubredditsPresenter


        @Binds
        abstract fun manageSubredditsUi(impl: ManageSubredditsUiImpl): ManageSubredditsUi

    }

    @PerFragment
    @Subcomponent(
        modules = [ManageSubredditsModule::class, FragmentModule::class]
    )
    interface ManageSubredditsFragmentComponent {
        @Subcomponent.Factory
        interface Factory {
            fun create(
                @BindsInstance fragment: BaseFragment
            ): ManageSubredditsFragmentComponent
        }

        fun inject(fragment: ManageSubredditsFragment)
    }


}