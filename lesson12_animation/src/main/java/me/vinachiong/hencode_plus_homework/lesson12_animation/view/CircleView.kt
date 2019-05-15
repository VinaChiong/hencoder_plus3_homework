package me.vinachiong.hencode_plus_homework.lesson12_animation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 * 环形进度条
 * @version vina.chiong@gmail.com
 */
class CircleView : View {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val RADIUS = Utils.dp2px(20f)

    var radius = RADIUS
        set(value) {
            field = value
            invalidate()
        }


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

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2f, height / 2f, radius, mPaint)
    }
}
