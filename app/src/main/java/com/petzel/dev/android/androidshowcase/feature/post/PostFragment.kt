package com.petzel.dev.android.androidshowcase.feature.post

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

private const val ARG_POST_ID = "post_id"

class PostFragment : BaseFragment() {

    @Inject
    lateinit var presenter: PostPresenter

    private var postId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getString(ARG_POST_ID)
        }
        Timber.d("onCreate viewing post fragment with postId == $postId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_view_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity!! as MainActivity).activityComponent!!.postFactory()
            .create(this, postId!!).inject(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(postId: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_POST_ID, postId)
                }
            }
    }

    @Module
    abstract class PostModule {

        @Binds
        abstract fun postPresenter(impl: PostPresenterImpl): PostPresenter

        @Binds
        abstract fun postUi(impl: PostUiImpl): PostUi

    }

    @PerFragment
    @Subcomponent(
        modules = [PostModule::class, FragmentModule::class]
    )
    interface PostFragmentComponent {
        @Subcomponent.Factory
        interface Factory {
            fun create(
                @BindsInstance fragment: BaseFragment,
                @BindsInstance postId: String
            ): PostFragmentComponent
        }

        fun inject(fragment: PostFragment)
    }


}