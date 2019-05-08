package me.vinachiong.hencode_plus_homework.lesson12_animation.view

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

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation = 0f
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

    private val camera = Camera()
    private fun init(attrs: AttributeSet?, defStyle: Int) {

        camera.setLocation(0f, 0f, Utils.getZForCamera().toFloat()) // -8 * 72
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        val tran = (IMAGE_PADDING + IMAGE_WIDTH / 2)
        canvas.translate(tran, tran)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        canvas.rotate(flipRotation)
        canvas.translate(-tran, -tran)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()


        canvas.save()
        canvas.translate(tran, tran)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        canvas.rotate(flipRotation)
        canvas.translate(-tran, -tran)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()
    }
}
