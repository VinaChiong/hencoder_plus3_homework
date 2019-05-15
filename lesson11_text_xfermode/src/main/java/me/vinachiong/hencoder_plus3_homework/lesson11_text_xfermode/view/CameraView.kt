package me.vinachiong.hencoder_plus3_homework.lesson11_text_xfermode.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 * CameraView
 *
 * 通过标记位累加，相继显示对应的Canvas几何变换效果：
 * translate、rotate、clipRect、clipOutPath、clipOutRect、skew、camera
 *
 *
 * @version vina.chiong@gmail.com
 */
class CameraView : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap: Bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
    private val rect = RectF()
    private val path = Path()

    private var canvasXferType = 0
        set(type) {
            field = type
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

    private val camera = Camera()
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        camera.rotateX(45f)
        camera.setLocation(0f, 0f, Utils.getZForCamera().toFloat())
        textPaint.textSize = Utils.dp2px(15f)
        setOnClickListener {
            canvasXferType = ++canvasXferType % 8
        }
        rect.set(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH / 3f, IMAGE_WIDTH + IMAGE_WIDTH / 4f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (canvasXferType) {
            0 -> default(canvas)
            1 -> translate(canvas)
            2 -> rotate(canvas)
            3 -> clipRect(canvas)
            4 -> clipOutPath(canvas)
            5 -> clipOutRect(canvas)
            6 -> skew(canvas)
            7 -> camera(canvas)
        }
    }

    private fun default(canvas: Canvas) {
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.drawText("default", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun rotate(canvas: Canvas) {
        canvas.save()
        canvas.rotate(15f, IMAGE_PADDING + IMAGE_WIDTH / 2f, IMAGE_PADDING + IMAGE_WIDTH / 2f)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
        canvas.drawText("rotate 15f", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun clipRect(canvas: Canvas) {
        canvas.save()
        canvas.clipRect(rect)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
        canvas.drawText("clipRect", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun clipOutPath(canvas: Canvas) {
        canvas.save()
        path.reset()
        path.addRect(rect, Path.Direction.CW)
        canvas.clipOutPath(path)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
        canvas.drawText("clipOutPath", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun clipOutRect(canvas: Canvas) {
        canvas.save()
        canvas.clipOutRect(rect)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
        canvas.drawText("clipOutRect", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun skew(canvas: Canvas) {
        canvas.save()
        val xDegree = 60.0
        canvas.skew(Math.tan(xDegree).toFloat(), 0f)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
        canvas.drawText("skew: x degree = $xDegree", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun translate(canvas: Canvas) {
        canvas.save()
        canvas.translate(Utils.dp2px(20f), Utils.dp2px(20f))
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
        canvas.drawText("translate", 0f, Utils.dp2px(25f), textPaint)
    }

    private fun camera(canvas: Canvas) {
        canvas.save()
        val tran = (IMAGE_PADDING + IMAGE_WIDTH / 2)
        canvas.translate(tran, tran)
        canvas.rotate(-30f)
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        canvas.rotate(30f)
        canvas.translate(-tran, -tran)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()


        canvas.save()
        canvas.translate(tran, tran)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        canvas.rotate(30f)
        canvas.translate(-tran, -tran)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        canvas.drawText("camera", 0f, Utils.dp2px(25f), textPaint)
    }


    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(200f)
        private val IMAGE_PADDING = Utils.dp2px(100f)
    }
}
