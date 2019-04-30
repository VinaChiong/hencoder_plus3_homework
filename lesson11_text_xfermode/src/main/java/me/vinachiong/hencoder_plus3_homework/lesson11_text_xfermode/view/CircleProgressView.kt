package me.vinachiong.hencoder_plus3_homework.lesson11_text_xfermode.view

import android.content.Context
import android.graphics.*
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import me.vinachiong.hencoder_plus3_homework.R
import me.vinachiong.lib.Utils

/**
 * 环形进度条
 * @version vina.chiong@gmail.com
 */
class CircleProgressView : View {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF()
    private val bounds = Rect()

    private val RADIUS = Utils.dp2px(120f)
    private val BAR_WIDTH = Utils.dp2px(20f)

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
        /*
            分别画底盘，刻度、指针、指针动画
         */
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = Utils.dp2px(10f)

        mTextPaint.color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        mTextPaint.textSize = Utils.dp2px(45f)
        mTextPaint.typeface = Typeface.SERIF
        mTextPaint.textAlign = Paint.Align.CENTER

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制环形及进度条
        drawCircle(canvas, 0.75f)

        mTextPaint.textAlign = Paint.Align.CENTER
        val text = "abcd".toUpperCase()
        val pos = calculatePosition(canvas, text)
        canvas.drawText(text, pos[0], pos[1], mTextPaint)

        mTextPaint.textAlign = Paint.Align.LEFT
        mTextPaint.textSize = Utils.dp2px(15f)
        mTextPaint.getTextBounds(text, 0, text.length, bounds)
        canvas.drawText(text, (-bounds.left).toFloat(), -bounds.top + mTextPaint.fontSpacing, mTextPaint)
    }

    private fun calculatePosition(canvas: Canvas, text: String): FloatArray {

        mTextPaint.getTextBounds(text, 0, text.length, bounds)
        // 为什么「中点坐标」算法是「(bottom - top) / 2」 ？，解释的方法是以坐标原点为场景说明，
        // 在一维坐标中，
        // TODO 为什么要用 ascent 和 descent？
        val centerOfTextBound = (bounds.bottom - bounds.top) / 2

        val fontMetrics = mTextPaint.fontMetrics


//        val centerOfFontMetrics = fontMetrics.leading - (fontMetrics.bottom + fontMetrics.top) / 2
        val centerOfFontMetrics = mTextPaint.fontMetrics.run {
            leading - (bottom + top) / 2
        }

        val x = width / 2f
        val y = height / 2f + centerOfFontMetrics

        println(
            """
        bound.bottom, bound.top = ${bounds.bottom}, ${bounds.top}
        offset = $centerOfTextBound
        top = ${fontMetrics.top},
        ascent = ${fontMetrics.ascent},
        leading = ${fontMetrics.leading},
        descent = ${fontMetrics.descent},
        bottom = ${fontMetrics.bottom},
        """
        )

        return floatArrayOf(x, y)
    }

    private fun drawCircle(canvas: Canvas, progress: Float) {
        mPaint.color = ResourcesCompat.getColor(resources, android.R.color.darker_gray, null)
        canvas.drawCircle(width / 2f, height / 2f, RADIUS, mPaint)

        mPaint.color = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        canvas.drawArc(mRectF, -90f, 360f * progress, false, mPaint)
    }

}
