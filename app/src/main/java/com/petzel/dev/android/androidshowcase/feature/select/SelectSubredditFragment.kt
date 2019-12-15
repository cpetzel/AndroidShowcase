package com.petzel.dev.android.androidshowcase.feature.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.petzel.dev.android.androidshowcase.App
import com.petzel.dev.android.androidshowcase.R
import com.petzel.dev.android.androidshowcase.di.AppComponent
import com.petzel.dev.android.androidshowcase.di.PerFragment
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.scope
import dagger.*
import timber.log.Timber
import javax.inject.Inject

class SelectSubredditFragment : Fragment() {

    @Inject
    lateinit var presenter: SelectSubredditPresenter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")
        DaggerSelectSubredditFragment_SelectSubredditFragmentComponent.factory()
            .create((activity?.application as App).appComponent, (activity as AppCompatActivity?)!!)
            .inject(this)

        super.onCreate(savedInstanceState)

        presenter.onCreate()
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
        abstract fun selectSubredditUi(ui: SubredditSelectUiImpl): SubredditSelectUi

        @Binds
        abstract fun selectSubredditPresenter(impl: SelectSubredditPresenterImpl): SelectSubredditPresenter

        @Module
        companion object {
            @PerFragment
            @Provides
            @JvmStatic
            fun scopeProvider(activity: AppCompatActivity): ScopeProvider = activity.scope()
        }
    }

    @PerFragment
    @Component(
        dependencies = [AppComponent::class],
        modules = [SelectSubredditModule::class]
    )
    interface SelectSubredditFragmentComponent {
        @Component.Factory
        interface Factory {
            fun create(
                mainComponent: AppComponent,
                @BindsInstance activity: AppCompatActivity
            ): SelectSubredditFragmentComponent
        }

        fun inject(fragment: SelectSubredditFragment)
    }

}