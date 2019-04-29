package me.vinachiong.hencoder_plus_homework.lesson10_drawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 * 仪表盘
 * @version vina.chiong@gmail.com
 */
class PieChartView : View {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF()

    private val RADIUS = Utils.dp2px(120f)


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private val colors = arrayOf("#008577", "#00574B", "#D81B60", "#4444FF")


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        /*
            分别画底盘，刻度、指针、指针动画
         */
        mPaint.style = Paint.Style.FILL

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.set(
            width/2f - RADIUS, height/2f - RADIUS,
            width/2f + RADIUS, height/2f + RADIUS
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startAngle = 0f
        colors.forEachIndexed { index, s ->
            mPaint.color = Color.parseColor(s)
            if (index == 1) {
                canvas.save()
                canvas.translate(
                    Math.cos(Math.toRadians((45f + startAngle).toDouble())).toFloat() * Utils.dp2px(10f),
                    Math.sin(Math.toRadians((45f + startAngle).toDouble())).toFloat() * Utils.dp2px(10f)
                )
            }
            canvas.drawArc(mRectF, startAngle, 90f, true, mPaint)
            if (index == 1) {
                canvas.restore()
            }
            startAngle += 90f
        }
    }

}
