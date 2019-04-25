package me.vinachiong.hencoder_plus_homework.lesson10_drawing.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.vinachiong.hencoder_plus_homework.lesson10_drawing.R

/**
 * 仪表盘
 * @version vina.chiong@gmail.com
 */
class DashBoardView : View {


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
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }
}
