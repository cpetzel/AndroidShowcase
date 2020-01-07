package com.petzel.dev.android.androidshowcase

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.petzel.dev.android.androidshowcase.core.Navigator
import com.petzel.dev.android.androidshowcase.core.NavigatorImpl
import com.petzel.dev.android.androidshowcase.di.PerActivity
import com.petzel.dev.android.androidshowcase.feature.feed.FeedFragment
import com.petzel.dev.android.androidshowcase.feature.managesubreddit.ManageSubredditsFragment
import com.petzel.dev.android.androidshowcase.feature.select.SelectSubredditFragment
import com.petzel.dev.android.androidshowcase.feature.subreddit.ViewSubredditFragment
import dagger.*
import de.mateware.snacky.Snacky
import kotlinx.android.synthetic.main.activity_main.*
import me.zhanghai.android.materialprogressbar.MaterialProgressBar
import timber.log.Timber
import javax.inject.Inject

interface Ui {
    fun showProgress(show: Boolean)
    fun snackError(message: String)
    fun snackInfo(message: String)
}

abstract class AppUi(private val activity: FragmentActivity) : Ui {
    override fun showProgress(show: Boolean) {
        try {
            activity.findViewById<MaterialProgressBar>(R.id.progressBar).visibility =
                if (show) View.VISIBLE else View.GONE
        } catch (e: Exception) {
            Timber.w("Implement ProgressUi, but did not provide R.id.progressBar of type MaterialProgressBar")
        }

        try {
            val refreshLayout = activity.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
            refreshLayout!!.isRefreshing = show
        } catch (e: Exception) {
            Timber.w("Implement ProgressUi, but did not provide R.id.swipeRefresh of type SwipeRefreshLayout")
        }
    }

    override fun snackError(message: String) {
        Snacky.builder().setActivity(activity).setText(message).error().show()
    }

    override fun snackInfo(message: String) {
        Snacky.builder().setActivity(activity).setText(message).info().show()
    }
}

class MainActivity : AppCompatActivity() {

    var activityComponent: MainActivityComponent? = null

    @Inject
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent =
            (application as App).appComponent.mainActivityComponentFactory().create(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        activityComponent!!.inject(this)
        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(
                navController,
                AppBarConfiguration(navController.graph, drawerLayout)
            )
    }

    override fun onDestroy() {
        activityComponent = null
        super.onDestroy()
    }

}

@Module
abstract class NavigationModule {

    @Binds
    abstract fun navigator(impl: NavigatorImpl): Navigator

    @Module
    companion object {
        @PerActivity
        @Provides
        @JvmStatic
        fun navController(activity: FragmentActivity): NavController =
            activity.findNavController(R.id.my_nav_host_fragment)
    }
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
    fun selectSubredditFactory(): SelectSubredditFragment.SelectSubredditFragmentComponent.Factory
    fun feedFactory(): FeedFragment.FeedFragmentComponent.Factory
    fun manageSubredditsFactory(): ManageSubredditsFragment.ManageSubredditsFragmentComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance activity: FragmentActivity
        ): MainActivityComponent
    }

    fun inject(activity: MainActivity)
}
