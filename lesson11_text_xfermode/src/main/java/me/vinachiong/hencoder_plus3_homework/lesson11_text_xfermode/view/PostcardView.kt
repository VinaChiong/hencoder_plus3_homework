package me.vinachiong.hencoder_plus3_homework.lesson11_text_xfermode.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.vinachiong.hencoder_plus3_homework.R
import me.vinachiong.lib.Utils

/**
 * 文图海报
 * @version vina.chiong@gmail.com
 */
class PostcardView : View {
    private val IMAGE_WIDTH = Utils.dp2px(150f)
    private val IMAGE_PADDING = Utils.dp2px(80f)
    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val measureWidth = FloatArray(1)
    private val bitmap: Bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private val metrics = Paint.FontMetrics()
    private fun init(attrs: AttributeSet?, defStyle: Int) {

        mTextPaint.textSize = Utils.dp2px(16f)
        mTextPaint.getFontMetrics(metrics)
    }

    private var measureWidths = FloatArray(1)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bitmap, width - IMAGE_WIDTH, IMAGE_PADDING, mTextPaint)

        val longText = context.getString(R.string.long_text)

        val length = longText.length
        var start = 0
        var count = 0
        var yOffset = 0f
        while (start < length) {
            yOffset += mTextPaint.fontSpacing
            start += count
            val textTop = yOffset + metrics.ascent
            val textBottom = yOffset + metrics.descent
            val usableWidth =
                if (textTop > IMAGE_PADDING && textTop < IMAGE_PADDING + IMAGE_WIDTH
                    || textBottom > IMAGE_PADDING && textBottom < IMAGE_PADDING + IMAGE_WIDTH) {
                    width - IMAGE_WIDTH
                } else {
                    width.toFloat()
                }

            count = mTextPaint.breakText(
                longText, start, length, true,
                usableWidth, measureWidth
            )
            canvas.drawText(longText, start, start + count, 0f, yOffset, mTextPaint)
            println("start: $start, end: ${start + count}, measureWidth: ${measureWidth[0]} ")
        }
    }
}
