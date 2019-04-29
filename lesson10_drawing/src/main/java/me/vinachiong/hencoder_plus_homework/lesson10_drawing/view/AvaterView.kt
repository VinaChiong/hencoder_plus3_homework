package me.vinachiong.hencoder_plus_homework.lesson10_drawing.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.vinachiong.hencoder_plus_homework.lesson10_drawing.R
import me.vinachiong.lib.Utils

/**
 * 仪表盘
 * @version vina.chiong@gmail.com
 */
class AvaterView : View {


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var cut = RectF()
    private var border = RectF()
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

    private lateinit var mBitmap: Bitmap

    private val WIDTH = Utils.dp2px(300f)
    private val PADDING = Utils.dp2px(40f)
    private val BORDER_WIDTH = Utils.dp2px(10f)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, option)
        option.inJustDecodeBounds = false
        option.inDensity = option.outWidth
        option.inTargetDensity = WIDTH.toInt()
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, option)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cut.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH)
        border.set(
            PADDING - BORDER_WIDTH,
            PADDING - BORDER_WIDTH,
            PADDING + WIDTH + BORDER_WIDTH,
            PADDING + WIDTH + BORDER_WIDTH)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 在正方形Rect内画一个Bitmap
        canvas.drawOval(border, paint)
        val saved = canvas.saveLayer(cut, paint)
        canvas.drawOval(cut, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(mBitmap, PADDING, PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(saved)

        // 设置Xfermode

        // 以正方形Rect区域的中心为圆心，正方形Rect边长/2 为半径画园
    }

}
