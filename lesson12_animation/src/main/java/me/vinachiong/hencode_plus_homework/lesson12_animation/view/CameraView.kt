package me.vinachiong.hencode_plus_homework.lesson12_animation.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap: Bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())

    //    private var topFlip = 0f
//        set(value) {
//            field = value
//            invalidate()
//        }
//    private var bottomFlip = 0f
//        set(value) {
//            field = value
//            invalidate()
//        }
//    private var flipRotation = 0f
//        set(value) {
//            field = value
//            invalidate()
//        }
    private val rotateAnimator = ObjectAnimator.ofFloat(this@CameraView, "rotate", START_ANGLE, (270f + 180f) + 360f * 2)

    private val topCameraRotateXAnimator = ObjectAnimator.ofFloat(this@CameraView, "bottomCameraRotateX", 0f, 45f)
    private val bottomCameraRotateXAnimator = ObjectAnimator.ofFloat(this@CameraView, "topCameraRotateX", 0f, -45f)
    val set = AnimatorSet()
    private val camera = Camera()
    var bottomCameraRotateX = 0f
        set(value) {
            field = value
            invalidate()
        }
    var topCameraRotateX = 0f
        set(value) {
            field = value
            invalidate()
        }
    var rotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        set.playSequentially(bottomCameraRotateXAnimator, rotateAnimator, topCameraRotateXAnimator)
        set.startDelay = 0L
        set.duration = 1500L
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
        camera.setLocation(0f, 0f, Utils.getZForCamera().toFloat()) // -8 * 72
        setOnClickListener {
            set.cancel()
            rotate = START_ANGLE
            bottomCameraRotateX = 0f
            topCameraRotateX = 0f
            set.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(TRANSLATE_DISTANCE, TRANSLATE_DISTANCE)
        canvas.rotate(-rotate)
        camera.save()
        camera.rotateX(topCameraRotateX)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(TOP_CLIP_RECT)
        canvas.rotate(rotate)
        canvas.translate(-TRANSLATE_DISTANCE, -TRANSLATE_DISTANCE)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        canvas.save()
        canvas.translate(TRANSLATE_DISTANCE, TRANSLATE_DISTANCE)
        canvas.rotate(-rotate)
        camera.save()
        camera.rotateX(bottomCameraRotateX)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(BOTTOM_CLIP_RECT)
        canvas.rotate(rotate)
        canvas.translate(-TRANSLATE_DISTANCE, -TRANSLATE_DISTANCE)
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

    }

    companion object {
        private val IMAGE_WIDTH = Utils.dp2px(200f)
        private val IMAGE_PADDING = Utils.dp2px(100f)
        private val TRANSLATE_DISTANCE = (IMAGE_PADDING + IMAGE_WIDTH / 2)
        private val TOP_CLIP_RECT = RectF(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        private val BOTTOM_CLIP_RECT = RectF(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        private const val START_ANGLE = 180f
    }
}
