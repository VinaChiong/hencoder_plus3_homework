package me.vinachiong.hencoder_plus_homework.lesson13_bitmap_drawable.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.*
import android.util.AttributeSet
import android.view.View
import me.vinachiong.lib.Utils

/**
 *
 *
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class DrawableView : View {

    private val colorDrawable: Drawable = ColorDrawable()
    private lateinit var bitmapDrawable: BitmapDrawable
    private lateinit var clipDrawable: ClipDrawable
    private lateinit var gradientDrawable: GradientDrawable
    private lateinit var ninePatchDrawable: NinePatchDrawable
    private lateinit var layerDrawable: LayerDrawable
    private lateinit var stateListDrawable: StateListDrawable
    private lateinit var levelListDrawable: LevelListDrawable
    private lateinit var transitionDrawable: TransitionDrawable
    private lateinit var scaleDrawable: ScaleDrawable

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

        bitmapDrawable = BitmapDrawable(resources, Utils.getAvatar(resources, Utils.dp2px(100f).toInt()))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        colorDrawable.setBounds(Utils.dp2px(50f).toInt(), Utils.dp2px(50f).toInt(), width, height)
        colorDrawable.draw(canvas)


    }
}