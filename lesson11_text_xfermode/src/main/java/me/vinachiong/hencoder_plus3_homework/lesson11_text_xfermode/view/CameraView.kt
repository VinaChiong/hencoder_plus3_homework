package me.vinachiong.hencoder_plus3_homework.lesson11_text_xfermode.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 * CameraView
 * @version vina.chiong@gmail.com
 */
class CameraView : View {


    private val IMAGE_WIDTH = Utils.dp2px(200f)
    private val IMAGE_PADDING = Utils.dp2px(100f)
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap: Bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
    private val rect = RectF()
    private val path = Path()

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private val camera = Camera()
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        camera.rotateX(45f)
        camera.setLocation(0f, 0f, Utils.getZForCamera().toFloat()) // -8 * 72
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        val tran = (IMAGE_PADDING + IMAGE_WIDTH / 2)
        canvas.translate(tran, tran)
        canvas.rotate(-60f)
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        canvas.rotate(60f)
        canvas.translate(-tran, -tran)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()


        canvas.save()
        canvas.translate(tran, tran)
        canvas.rotate(-60f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        canvas.rotate(60f)
        canvas.translate(-tran, -tran)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
    }
}
