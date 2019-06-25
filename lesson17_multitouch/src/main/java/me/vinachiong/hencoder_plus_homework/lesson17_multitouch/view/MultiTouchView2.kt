package me.vinachiong.hencoder_plus_homework.lesson17_multitouch.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
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
class MultiTouchView2 : View {
    private lateinit var bitmap: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var downX: Float = 0f
    private var downY: Float = 0f

    private var targetOffsetX = 0f
    private var targetOffsetY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

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
        textPaint.textSize = Utils.dp2px(20f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, targetOffsetX, targetOffsetY, paint)
        drawTitle(canvas)
    }

    private fun drawTitle(canvas: Canvas) {
        val bounds = Rect().apply {
            textPaint.getTextBounds(TITLE, 0, TITLE.length, this)
        }

        val left = (width.toFloat() - (bounds.right - bounds.left)) / 2f
        val top = textPaint.fontSpacing

        canvas.drawText(TITLE, 0, TITLE.length, left, top, textPaint)
    }


    private var focusPointX = 0f
    private var focusPointY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 协作型
        var sumX = 0f
        var sumY = 0f
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP
        (0 until event.pointerCount).forEach {
            if (!(isPointerUp && event.actionIndex == it)) {
                sumX += event.getX(it)
                sumY += event.getY(it)
            }
            Log.i(TAG, "[ACTION_POINTER_DOWN] indext $it pointer id = ${event.getPointerId(it)}")
        }

        val pointerCount =
            if (isPointerUp) {
                event.pointerCount - 1
            } else {
                event.pointerCount
            }
        focusPointX = sumX / pointerCount
        focusPointY = sumY / pointerCount

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_UP,
            MotionEvent.ACTION_POINTER_DOWN -> {
                downX = focusPointX
                downY = focusPointY
                offsetX = targetOffsetX
                offsetY = targetOffsetY
            }
            MotionEvent.ACTION_MOVE -> {
                targetOffsetX = offsetX + (focusPointX - downX)
                targetOffsetY = offsetY + (focusPointY - downY)
                invalidate()
            }
        }

        return true
    }


    companion object {
        private const val TAG = "MultiTouchView"
        private const val TITLE = "多点触控共同作用焦点"
        private val TITLE_TEXT_SIZE = Utils.dp2px(20f)
        private val IMAGE_WIDTH = Utils.dp2px(300f)
    }
}