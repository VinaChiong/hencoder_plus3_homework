package me.vinachiong.hencoder_plus_homework.lesson17_multitouch.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import me.vinachiong.lib.Utils

/**
 *
 *
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class MultiTouchView : View {
    private lateinit var bitmap: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var downX: Float = 0f
    private var downY: Float = 0f

    private var targetOffsetX = 0f
    private var targetOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f
    private var touchPointerId = 0

    constructor(context: Context?) : super(context) {
        init(null, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, null)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int?) {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, targetOffsetX, targetOffsetY, paint)
    }

//    private val pointerIds = mutableListOf<Int>()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                offsetX = targetOffsetX
                offsetY = targetOffsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.i(TAG, "[ACTION_POINTER_DOWN] actionIndex = ${event.actionIndex}, pointerCount = ${event.pointerCount}")
                (0 until event.pointerCount).forEach {
                    event.getPointerId(it)
                    Log.i(TAG, "[ACTION_POINTER_DOWN] indext $it pointer id = ${event.getPointerId(it)}")
                }

                touchPointerId = event.getPointerId(event.actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
                offsetX = targetOffsetX
                offsetY = targetOffsetY
            }
            MotionEvent.ACTION_MOVE -> {

                val x = event.getX(event.findPointerIndex(touchPointerId))
                val y = event.getY(event.findPointerIndex(touchPointerId))

                targetOffsetX = offsetX + (x - downX)
                targetOffsetY = offsetY + (y - downY)
                invalidate()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                Log.i(TAG, "[ACTION_POINTER_UP] actionIndex = ${event.actionIndex}, pointerCount = ${event.pointerCount}")
                Log.i(TAG, "[ACTION_POINTER_UP] actionIndex = ${event.actionIndex}")
                val pointerId = event.getPointerId(event.actionIndex)
                if (pointerId == touchPointerId) {
                    val newIndex: Int = if (event.actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    touchPointerId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    offsetX = targetOffsetX
                    offsetY = targetOffsetY
                }
            }
        }


        return true
    }


    companion object {
        private const val TAG = "MultiTouchView"
        private val IMAGE_WIDTH = Utils.dp2px(300f)
    }
}