package me.vinachiong.hencoder_plus_homework.lesson10_drawing.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 * 仪表盘
 * @version vina.chiong@gmail.com
 */
class DashBoardView : View {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDashPath = Path()
    private val mPath = Path()
    private val mRectF = RectF()
    private lateinit var mPathMeasure: PathMeasure

    private lateinit var mPathEffect: PathDashPathEffect

    private val RADIUS = Utils.dp2px(150f)
    private val LENGTH = Utils.dp2px(110f)
    private val ANGLE = 140f


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
        mPaint.strokeWidth = Utils.dp2px(1f)
        mPaint.color = ResourcesCompat.getColor(Resources.getSystem(), android.R.color.black, null)

        mDashPath.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(10f), Path.Direction.CCW)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.set(
            width / 2 - RADIUS, height / 2 - RADIUS,
            width / 2 + RADIUS, height / 2 + RADIUS
        )
        //
        mPath.addArc(mRectF, 90 + ANGLE / 2, 360 - ANGLE)
        mPathMeasure = PathMeasure(mPath, false)

        // PathDashPathEffect的意思：在一个Path的线路上，每隔一段距离，用另外一个Path对象画一个几何图形
        mPathEffect = PathDashPathEffect(
            mDashPath,
            (mPathMeasure.length - Utils.dp2px(2f)) / 20,
            0f,
            PathDashPathEffect.Style.MORPH
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画弧形
        canvas.drawPath(mPath, mPaint)

        // 添加PathEffect, 画刻度
        mPaint.pathEffect = mPathEffect
        canvas.drawPath(mPath, mPaint)
        mPaint.pathEffect = null


        val startx = width / 2f
        val starty = height / 2f
//        val x = Math.sin(Math.toRadians((ANGLE / 2).toDouble())).toFloat() * LENGTH
//        val y = Math.cos(Math.toRadians((ANGLE / 2).toDouble())).toFloat() * LENGTH
        val x = Math.sin(calculateAngleToRadians(0.26)).toFloat() * LENGTH
        val y = Math.cos(calculateAngleToRadians(0.26)).toFloat() * LENGTH
        println("startx = $startx, starty = $starty")
        println("x = $x, y = $y")
        // 画指针
        canvas.drawLine(startx, starty, startx - x, starty + y, mPaint)
    }

    private fun calculateAngleToRadians(percentage: Double): Double {
        // 以圆弧的圆心为坐标原点，刻度为0的对应角度为
        val startAngle = ANGLE / 2
        // 总共可转动的弧度角度
        val totalAngle = (360f - ANGLE)
        // 目标角度 = 开始角度 + 可转角度 * 百分比
        return Math.toRadians(startAngle + totalAngle * percentage)
    }
}
