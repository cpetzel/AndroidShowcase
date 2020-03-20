package com.petzel.dev.android.androidshowcase

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.core.NavigatorImpl
import com.petzel.dev.android.androidshowcase.core.TitleProvider
import com.petzel.dev.android.androidshowcase.di.PerActivity
import com.petzel.dev.android.androidshowcase.feature.feed.FeedFragment
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.ManageSubredditsFragment
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.workflow.ManageSubredditsWorkflowFragment
import com.petzel.dev.android.androidshowcase.feature.post.PostFragment
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewSubredditFragment
import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import de.mateware.snacky.Snacky
import timber.log.Timber


interface Ui {
    fun showProgress(show: Boolean)
    fun snackError(message: String)
    fun snackInfo(message: String)
}

abstract class AppUi(private val fragmentRootView: View) : Ui {
    override fun showProgress(show: Boolean) {
        try {
            val refreshLayout = fragmentRootView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
            refreshLayout!!.isRefreshing = show
        } catch (e: Exception) {
            Timber.w("Implement ProgressUi, but did not provide R.id.swipeRefresh of type SwipeRefreshLayout")
        }
    }

    override fun snackError(message: String) {
        Snacky.builder().setActivity(fragmentRootView.context.getActivity()).setText(message)
            .error().show()
    }

    override fun snackInfo(message: String) {
        Snacky.builder().setActivity(fragmentRootView.context.getActivity()).setText(message).info()
            .show()
    }
}

class MainActivity : AppCompatActivity() {

    var activityComponent: MainActivityComponent? = null

    val toolbar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }
    val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawerLayout) }

    val drawerToggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(
            this,
            drawerLayout,
            android.R.string.copy,
            android.R.string.cancel
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent =
            (application as App).appComponent.mainActivityComponentFactory()
                .create(this, drawerLayout)

        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        activityComponent!!.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, FeedFragment(), "FEED").commit()
        }
    }


    override fun onDestroy() {
        Timber.d("onDestroy")
        activityComponent = null
        super.onDestroy()
    }

    fun setupToolbar() {

        setSupportActionBar(toolbar)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        supportFragmentManager.addOnBackStackChangedListener {

            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            if (currentFragment is TitleProvider) {
                currentFragment.title?.let {
                    supportActionBar?.title = it
                }
            }

            if (supportFragmentManager.backStackEntryCount > 0) {
                //show back button
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                drawerToggle.isDrawerIndicatorEnabled = false
            } else {
                drawerToggle.isDrawerIndicatorEnabled = true
            }
        }
    }
}

@Module
abstract class NavigationModule {

    @Binds
    abstract fun navigator(impl: NavigatorImpl): Navigator

}

@PerActivity
@Subcomponent(
    modules = [NavigationModule::class]
)
interface MainActivityComponent {

    // exposing this for dependent components
    fun nav(): Navigator

    fun activity(): FragmentActivity

    fun viewSubredditFactory(): ViewSubredditFragment.ViewSubredditFragmentComponent.Factory
    fun feedFactory(): FeedFragment.FeedFragmentComponent.Factory
    fun manageSubredditsFactory(): ManageSubredditsFragment.ManageSubredditsFragmentComponent.Factory
    fun postFactory(): PostFragment.PostFragmentComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: FragmentActivity,
            @BindsInstance drawerLayout: DrawerLayout
        ): MainActivityComponent
    }

    fun inject(activity: MainActivity)
    fun inject(manageSubredditsWorkflowFragment: ManageSubredditsWorkflowFragment)
}

tailrec fun Context?.getActivity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.getActivity()
}
