package com.petzel.dev.android.androidshowcase.feature.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.media.SoundPool
import android.view.MotionEvent
import android.view.SurfaceView
import java.util.*


class SnakeEngine(cont: Context, val size: Point) : SurfaceView(cont), Runnable {


    private var thread: Thread? = null

    // To hold a reference to the Activity

    // for plaing sound effects
    private val soundPool: SoundPool? = null
    private val eat_bob = -1
    private val snake_crash = -1

    // For tracking movement Heading
    enum class Heading {
        UP, RIGHT, DOWN, LEFT
    }

    // Start by heading to the right
    private var heading = Heading.RIGHT

    // To hold the screen size in pixels
    private var screenX: Int = 0
    private var screenY: Int = 0

    // How long is the snake
    private var snakeLength: Int = 0

    // Where is Bob hiding?
    private var bobX: Float? = 0F
    private var bobY: Float? = 0F

    // The size in pixels of a snake seFgment
    private var blockSize: Float = 0F

    // The size in segments of the playable area
    private val NUM_BLOCKS_WIDE = 40F
    private var numBlocksHigh: Float? = 0F

    // Control pausing between updates
    private var nextFrameTime: Long = 0

    // Update the game 10 times per second
    private val FPS: Long = 5

    // There are 1000 milliseconds in a second
    private val MILLIS_PER_SECOND: Long = 1000
    // We will draw the frame much more often

    // How many points does the player have
    private var score: Int = 0

    // The location in the grid of all the segments
    // If you score 200 you are rewarded with a crash achievement!
    private val snakeXs = FloatArray(200)
    private val snakeYs = FloatArray(200)

    // Everything we need for drawing
    // Is the game currently playing?
    @Volatile
    private var isPlaying: Boolean = false


    // Some paint for our canvas
    private val paint: Paint = Paint()

    init {
        screenX = size.x
        screenY = size.y

        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE
        // How many blocks of the same size will fit into the height
        numBlocksHigh = screenY.div(blockSize)
    }

    init {
        // Start the game
        newGame()
    }

    private fun newGame() {
        // Start with a single snake segment
        snakeLength = 1;
        snakeXs[0] = NUM_BLOCKS_WIDE!! / 2
        snakeYs[0] = numBlocksHigh!! / 2

        // Get Bob ready for dinner
        spawnBob()

        // Reset the score
        score = 0

        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis()
    }


    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> if (motionEvent.x >= screenX / 2) {
                when (heading) {
                    Heading.UP -> heading = Heading.RIGHT
                    Heading.RIGHT -> heading = Heading.DOWN
                    Heading.DOWN -> heading = Heading.LEFT
                    Heading.LEFT -> heading = Heading.UP
                }
            } else {
                when (heading) {
                    Heading.UP -> heading = Heading.LEFT
                    Heading.LEFT -> heading = Heading.DOWN
                    Heading.DOWN -> heading = Heading.RIGHT
                    Heading.RIGHT -> heading = Heading.UP
                }
            }
        }
        return true
    }

    fun update() {
        // Did the head of the snake eat Bob?
        if (snakeXs[0] == bobX && snakeYs[0] == bobY) {
            eatBob()
        }

        moveSnake()

        if (detectDeath()) {
            //start again
            newGame()
        }
    }

    private fun detectDeath(): Boolean {
        // Has the snake died?
        var dead = false

        // Hit the screen edge
        if (snakeXs[0] == -1F) dead = true
        if (snakeXs[0] >= NUM_BLOCKS_WIDE) dead = true
        if (snakeYs[0] == -1F) dead = true
        if (snakeYs[0] == numBlocksHigh) dead = true

        // Eaten itself?
        for (i in snakeLength - 1 downTo 1) {
            if (i > 4 && snakeXs[0] == snakeXs[i] && snakeYs[0] == snakeYs[i]) {
                dead = true
            }
        }

        return dead
    }

    private fun moveSnake() {
        // Move the body
        for (i in snakeLength downTo 1) {
            // Start at the back and move it
            // to the position of the segment in front of it
            snakeXs[i] = snakeXs[i - 1]
            snakeYs[i] = snakeYs[i - 1]

            // Exclude the head because
            // the head has nothing in front of it
        }

        // Move the head in the appropriate heading
        when (heading) {
            Heading.UP -> snakeYs[0]--

            Heading.RIGHT -> snakeXs[0]++

            Heading.DOWN -> snakeYs[0]++

            Heading.LEFT -> snakeXs[0]--
        }


    }

    /**
     * The spawnBob method uses two random int values within the ranges of zero and NUM_BLOCKS_WIDE,
     * zero and numBlocksHigh, then initializes the horizontal and vertical location of the mouse.
     */
    private fun spawnBob() {
        val random = Random()
        bobX = (random.nextInt((NUM_BLOCKS_WIDE - 1).toInt()) + 1).toFloat()
        bobY = (random.nextInt((numBlocksHigh!! - 1).toInt()) + 1).toFloat()
    }

    private fun eatBob() {
        // The snake’s length is increased by one block, a new mouse is spawned, 1 is added to the score
        // and a sound effect is played.
        // add a tail to the snake.
        //  Got him!
        // Increase the size of the snake
        snakeLength++
        //replace Bob
        // This reminds me of Edge of Tomorrow. Oneday Bob will be ready!
        spawnBob()
        //add to the score
        score += 1
    }

    override fun run() {
        while (isPlaying) {

            // Update 10 times a second
            if (updateRequired()) {
                update()
                draw()
            }
        }
    }

    fun draw() {

        if (holder.surface.isValid) {
            val canvas = holder.lockCanvas()

            // Fill the screen with Game Code School blue
            canvas.drawColor(Color.argb(255, 26, 128, 182))

            // Set the color of the paint to draw the snake white
            paint.color = Color.argb(255, 255, 255, 255)

            // Scale the HUD text
            paint.textSize = 90f
            canvas.drawText("Score:$score", 10F, 70F, paint)

            // Draw the snake one block at a time
            for (i in 0 until snakeLength) {
                canvas.drawRect(
                    snakeXs[i] * blockSize,
                    snakeYs[i] * blockSize,
                    snakeXs[i] * blockSize + blockSize,
                    snakeYs[i] * blockSize + blockSize,
                    paint
                )
            }

            // Set the color of the paint to draw Bob red
            paint.color = Color.argb(255, 255, 0, 0)

            // Draw Bob
            canvas.drawRect(
                bobX!! * blockSize,
                bobY!! * blockSize,
                bobX!! * blockSize + blockSize,
                bobY!! * blockSize + blockSize,
                paint
            )

            // Unlock the canvas and reveal the graphics for this frame
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun updateRequired(): Boolean {
        // Are we due to update the frame
        if (nextFrameTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS

            // Return true so that the update and draw
            // functions are executed
            return true
        }

        return false
    }

    fun pause() {
        isPlaying = false
        try {
            thread!!.join()
        } catch (e: InterruptedException) {
            // Error
        }
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this).also { it.start() }
    }
}