package com.petzel.dev.android.androidshowcase.feature.snake

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.petzel.dev.android.androidshowcase.core.BaseFragment
import android.view.Display
import android.view.SurfaceView



class SnakeFragment : BaseFragment() {

    var snakeEngine: SnakeEngine? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the pixel dimensions of the screen
        val display = activity!!.windowManager.defaultDisplay

        // Initialize the result into a Point object
        val size = Point()
        display.getSize(size)

        // Create a new instance of the SnakeEngine class
        snakeEngine = SnakeEngine(context!!, size)
        return snakeEngine
    }

    override fun onResume() {
        super.onResume()
        snakeEngine!!.resume()

    }

    override fun onPause() {
        super.onPause()
        snakeEngine!!.pause()
    }
}