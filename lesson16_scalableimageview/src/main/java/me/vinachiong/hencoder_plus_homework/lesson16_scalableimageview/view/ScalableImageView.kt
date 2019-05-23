package me.vinachiong.hencoder_plus_homework.lesson16_scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import android.widget.Scroller
import me.vinachiong.lib.Utils

/**
 * 处理双击缩放：
 * - 处理放大、缩小的计算
 * - 使用GestureDetector，监听双击、滑动、Fling等手势
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class ScalableImageView : View, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    private var big = false
    private var bigScala: Float = 0f
    private var smallScala: Float = 0f

    private var scalaFraction: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var scalaObjectAnimator: ObjectAnimator? = null

    private lateinit var bitmap: Bitmap
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var scroller: OverScroller


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        mDetector = GestureDetectorCompat(context, this)
        mDetector.setOnDoubleTapListener(this)
        scroller = OverScroller(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val bw = bitmap.width
        val bh = bitmap.height
        originalOffsetX = (width - bw) / 2f
        originalOffsetY = (height - bh) / 2f


        if (bw / bh > width / height) {
            smallScala = width.toFloat() / bw.toFloat()
            bigScala = height.toFloat() / bh.toFloat() * OVER_SCALE_FACTOR
        } else {
            bigScala = width.toFloat() / bw.toFloat()
            smallScala = height.toFloat() / bh.toFloat() * OVER_SCALE_FACTOR
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(offsetX, offsetY)
        val scale = smallScala + (bigScala - smallScala) * scalaFraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    private fun getScalaObjectAnimator(): ObjectAnimator {
        if (null == scalaObjectAnimator) {
            scalaObjectAnimator = ObjectAnimator.ofFloat(
                this@ScalableImageView,
                "scalaFraction", 0f, 1f
            )
        }
        return scalaObjectAnimator!!
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTap: $e")
        big = !big
        if (big) {
            getScalaObjectAnimator().start()
        } else {
            offsetX = 0f
            offsetY = 0f
            getScalaObjectAnimator().reverse()
        }

        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: $e")
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $e")
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
        Log.d(DEBUG_TAG, "onShowPress: $e")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $e")
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d(DEBUG_TAG, "onDown: $e")
        return true
    }

    override fun onFling(firstDown: MotionEvent?, currentFling: MotionEvent?,
                         velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun onScroll(
        firstDown: MotionEvent?,
        currentSroll: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if (big) {
            Log.d(DEBUG_TAG, "distanceX: $distanceX ，distanceY： $distanceY")
            offsetX -= distanceX
            offsetX = Math.min(offsetX, (bitmap.width * bigScala - width) / 2f)
            offsetX = Math.max(offsetX, - (bitmap.width * bigScala - width) / 2f)

            offsetY -= distanceY
            offsetY = Math.min(offsetY, (bitmap.height * bigScala - height) / 2f)
            offsetY = Math.max(offsetY, - (bitmap.height * bigScala - height) / 2f)
        }
        invalidate()

        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.d(DEBUG_TAG, "onLongPress: $e")
    }

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(300f)
        private const val DEBUG_TAG = "ScalableImageView"
        private const val OVER_SCALE_FACTOR = 1.5f
    }
}