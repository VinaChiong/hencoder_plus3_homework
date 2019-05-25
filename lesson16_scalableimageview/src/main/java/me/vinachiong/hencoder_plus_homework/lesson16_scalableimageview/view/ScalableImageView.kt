package me.vinachiong.hencoder_plus_homework.lesson16_scalableimageview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import me.vinachiong.lib.Utils

/**
 * 处理双击缩放：
 * - 处理放大、缩小倍数的计算
 * - 使用GestureDetector，监听双击、滑动、Fling等手势
 * - 滑动要解决的问题：
 *      -计算正确的可滑动距离，搞清楚 手势滑动方向、onScroll的distance正负值、图片要偏移的方向及算法
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class ScalableImageView : View {
    /** 画图画笔 */
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /** 初始的中心x偏移 */
    private var originOffsetX = 0f
    /** 初始的中心y偏移 */
    private var originOffsetY = 0f

    /** 是否显示大图，默认false */
    private var big = false
    /** 放大倍数 */
    private var scaleBig: Float = 0f
    /** 缩小（较小边紧贴）倍数*/
    private var scaleSmall: Float = 0f
    /** 当前的缩放倍数，在scaleBig和scaleSmall值之间*/
    private var currentScale: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

//    /** 缩放分数，用作动画值 */
//    private var scalaFraction: Float = 0f
//        set(value) {
//            field = value
//            invalidate()
//        }
    /** 缩放的ObjectAnimator */
    private var scalaObjectAnimator: ObjectAnimator? = null
    /** 要显示的图像Bitmap */
    private lateinit var bitmap: Bitmap
    /** GestureDetector */
    private lateinit var mDetector: GestureDetectorCompat
    /** ScaleGestureDetector */
    private lateinit var mScaleDetector: ScaleGestureDetector
    /** 自定义GestureDetectorListener，主要实现双击、惯性滑动 */
    private val gestureDetectorListener = GestureDetectorListener()
    /** 自定义ScaleListener，主要实现双指缩放 */
    private val scaleListener = ScaleListener()
    /** 滑动时候图片的X偏移 */
    private var scrollOffsetX = 0f
    /** 滑动时候图片的Y偏移 */
    private var scrollOffsetY = 0f

    /** OverScroller */
    private lateinit var mScroller: OverScroller
    private val flingRunnable = FlingRunnable()

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    /**
     * 初始化
     */
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        mDetector = GestureDetectorCompat(context, gestureDetectorListener)
        mDetector.setOnDoubleTapListener(gestureDetectorListener)
        mScroller = OverScroller(context)
        mScaleDetector = ScaleGestureDetector(context, scaleListener)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val bw = bitmap.width.toFloat()
        val bh = bitmap.height.toFloat()
        originOffsetX = (width - bw) / 2f
        originOffsetY = (height - bh) / 2f

        if (bw / bh > width / height) {
            scaleBig = (height.toFloat() / bh) * SCALE_FACTOR
            scaleSmall = (width.toFloat() / bw)
        } else {
            scaleBig = (width.toFloat() / bw) * SCALE_FACTOR
            scaleSmall = (height.toFloat() / bh)
        }
        currentScale = scaleSmall
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(ContextCompat.getColor(context, android.R.color.black))
//        canvas.translate(scrollOffsetX * scalaFraction, scrollOffsetY * scalaFraction)


        val fraction = (currentScale - scaleSmall) / (scaleBig - scaleSmall)
        canvas.translate(scrollOffsetX * fraction, scrollOffsetY * fraction)

//        val scale = scaleSmall + (scaleBig - scaleSmall) * scalaFraction
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint)
    }

    private fun getScalaObjectAnimator(): ObjectAnimator {
        if (null == scalaObjectAnimator) {
            scalaObjectAnimator = ObjectAnimator.ofFloat(
                this@ScalableImageView, "currentScale", 0f
            )
        }
        scalaObjectAnimator!!.setFloatValues(scaleSmall, scaleBig)
        return scalaObjectAnimator!!
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var result = mScaleDetector.onTouchEvent(event)
        if (!mScaleDetector.isInProgress) {
            result = mDetector.onTouchEvent(event)
        }
        return result
    }

    private inner class FlingRunnable : Runnable {
        override fun run() {
            if (mScroller.computeScrollOffset()) {
                scrollOffsetX = mScroller.currX.toFloat()
                scrollOffsetY = mScroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        private var initScale: Float = 0f
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // 缩放系数
            // 计算新的currentScale
            currentScale = initScale * detector.scaleFactor

            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initScale = currentScale
            return super.onScaleBegin(detector)
        }
    }

    private inner class GestureDetectorListener : GestureDetector.SimpleOnGestureListener(),
        GestureDetector.OnDoubleTapListener {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.i(TAG, "onDoubleTap big = $big")
            big = !big
            if (big) {
                // 双击放大后，双击的位置平移到屏幕中心
                scrollOffsetX = (e.x - width.toFloat() / 2f) * (1 - scaleBig / scaleSmall)
                scrollOffsetY = (e.y - height.toFloat() / 2f) * (1 - scaleBig / scaleSmall)
                adjustScrollOffset()
                getScalaObjectAnimator().start()
            } else {
//                scrollOffsetX = 0f
//                scrollOffsetY = 0f
                getScalaObjectAnimator().reverse()
            }

            return true
        }

        override fun onDown(e: MotionEvent?): Boolean = true


        override fun onFling(
            firstDown: MotionEvent,
            current: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d(TAG, "onFling 初始点击位置 ${firstDown.x}, ${firstDown.y}")
            Log.d(TAG, "onFling 当前手势位置 ${current.x}, ${current.y}")
            Log.d(TAG, "onFling velocityX = $velocityX, velocityY = $velocityY")

            if (big) {
                mScroller.fling(
                    scrollOffsetX.toInt(), scrollOffsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    -((bitmap.width * scaleBig - width) / 2).toInt(),
                    ((bitmap.width * scaleBig - width) / 2).toInt(),
                    -((bitmap.height * scaleBig - height) / 2).toInt(),
                    ((bitmap.height * scaleBig - height) / 2).toInt()
                )
                postOnAnimation(flingRunnable)
            }


            return false
        }

        override fun onScroll(
            firstDown: MotionEvent,
            current: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (!big) return true

            Log.d(TAG, "onScroll 初始点击位置 ${firstDown.x}, ${firstDown.y}")
            Log.d(TAG, "onScroll 当前手势位置 ${current.x}, ${current.y}")
            Log.d(TAG, "onScroll distanceX = $distanceX, distanceY = $distanceY")

            /*
                获取n组滑动事件的数据，每组数据表示系统能捕获的「单方向」上的「最小单位时间」内响应的「滑动事件信息」。
                约定简称：
                    distanceX => dX    | distanceX => dX
                    firstDown.x => fX  | firstDown.y => fY
                    current.x => fX  | current.y => fY

                 对数据进行分析得到关系：
                    n = 1时候，dX = fX - cX， dY = fY - cY
                    n > 1时候，dX = cX(n - 1) - cX(n), dY = cY(n - 1) - cY(n)
                 换一个思维理解，n = 0的时候， fX = cX, fY = cY，即未发生滑动，onScroll方法没有被调用。

                 所以(distanceX, distanceY)，表示滑动点的xy 坐标距离的差值，前者减后者:
                 {
                     if 方向 of (n-1, n) = 左 -> 右 { dX < 0 }
                     if 方向 of (n-1, n) = 右 -> 左 { dX > 0 }

                     if 方向 of (n-1, n) = 上 -> 下 { dY < 0 }
                     if 方向 of (n-1, n) = 下 -> 上 { dY > 0 }
                 }

                 dX(n) = cX(n-1) - cX(n)
                 dY(n) = cY(n-1) - cY(n)

                 对于 scrollOffsetX, scrollOffsetY, 第n次

                 scrollOffsetX(n) = scrollOffsetX(n-1) + (cX(n) - cX(n-1))
                 scrollOffsetY(n) = scrollOffsetY(n-1) + (cY(n) - cY(n-1))

                 代入公式：
                 scrollOffsetX(n) = scrollOffsetX(n-1) - dX(n)
                 scrollOffsetY(n) = scrollOffsetY(n-1) - dY(n)
             */
            // 累计偏移
            scrollOffsetX -= distanceX
            scrollOffsetY -= distanceY

            // 限制偏移量，不能超越缩放后的边界
            adjustScrollOffset()

            invalidate()
            return true
        }
    }

    private fun adjustScrollOffset() {
        // 任意方向的滑动范围 转化为 scrollOffsetX, scrollOffsetX的正负值区间
        val diffX = (width - bitmap.width.toFloat() * scaleBig) / 2f
        val diffY = (height - bitmap.height.toFloat() * scaleBig) / 2f

        // 向左是负数，向右是正数
        scrollOffsetX = Math.min(scrollOffsetX, -diffX)
        scrollOffsetX = Math.max(diffX, scrollOffsetX)
        // 向上是负数，向下是正数
        scrollOffsetY = Math.min(scrollOffsetY, -diffY)
        scrollOffsetY = Math.max(diffY, scrollOffsetY)
    }

    companion object {
        private const val SCALE_FACTOR = 1.5f
        private val IMAGE_WIDTH = Utils.dp2px(400f)
        private const val TAG = "ScalableImageView"
    }
}