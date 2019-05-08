package me.vinachiong.hencoder_plus_homework.lesson13_bitmap_drawable.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 *
 *
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class BitmapView : View {

    private lateinit var SAMPLE_BITMAP:Bitmap
    private lateinit var bitmap1: Bitmap
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

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
        // Enum
        //Bitmap.Config
        //ALPHA_8 1 byte/pixel
        //RGB_565 2 byte/pixel
        //ARGB_4444 4 byte/pixel，@Deprecated 4.4,
        //ARGB_8888 4 byte/pixel，
        //RGBA_F16 8 byte/pixel
        //HARDWARE 特殊配置，Bitmap仅用作draw on屏幕时候, 意味着这个Bitmap对象是不可变

        // Bitmap.CompressFormat
        // JPG, PNG, WEBP 三种压缩格式
//        BitmapFactory
//        BitmapFactory.Options
//        BitmapRegionDecoder
//        BitmapShader
        SAMPLE_BITMAP = Utils.getAvatar(resources, Utils.dp2px(100f).toInt())
        bitmap1 = createImmutableFromBitmap()
        textPaint.color = Color.parseColor("#000000")
        textPaint.textSize = Utils.dp2px(20f)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap1,  Utils.dp2px(50f), 0f, paint)
        canvas.drawText("${bitmap1.generationId}",  Utils.dp2px(100f), Utils.dp2px(50f), textPaint)

    }

    private fun createImmutableFromBitmap(): Bitmap {

        return Bitmap.createBitmap(SAMPLE_BITMAP)
    }

//    private fun createImmutableFromColors(): Bitmap {
//        val w = Utils.dp2px(100f).toInt()
//        val h = Utils.dp2px(100f).toInt()
//        val pixels = IntArray(w * h)
//        SAMPLE_BITMAP.getPixels(pixels, Utils.dp2px(10f).toInt(), w, 0, 0, w, h)
//        return Bitmap.createBitmap(pixels, w, h, Bitmap.Config.ARGB_4444)
//    }

//    private fun createImmutableFromPicture(): Bitmap {
//
//    }
//
//    private fun createMutableByColorSpace(): Bitmap {
//
//    }
}



