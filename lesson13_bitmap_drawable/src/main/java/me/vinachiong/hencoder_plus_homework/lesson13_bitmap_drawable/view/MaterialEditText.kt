package me.vinachiong.hencoder_plus_homework.lesson13_bitmap_drawable.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import me.vinachiong.lib.Utils

/**
 *
 *
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class MaterialEditText : AppCompatEditText {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var useFloatingLabel = true
    private var animator: ObjectAnimator? = null
    private var floatingLabelShown = false
    private val paddingRect = Rect()

    var floatingLabelFraction: Float = 0f
        set(floatingLabelFraction) {
            field = floatingLabelFraction
            invalidate()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        hint = "hint Text"

        paint.textSize = TEXT_SIZE
        background.getPadding(paddingRect)
        if (useFloatingLabel) {
            setPadding(
                paddingRect.left,
                (paddingRect.top + TEXT_SIZE + TEXT_MARGIN).toInt(),
                paddingRect.right,
                paddingRect.bottom
            )
        } else {
            setPadding(
                paddingRect.left,
                paddingRect.top,
                paddingRect.right,
                paddingRect.bottom
            )
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (floatingLabelShown && s.isEmpty()) {
                    floatingLabelShown = !floatingLabelShown
                    getAnimator()!!.reverse()
                } else if (!floatingLabelShown && s.isNotEmpty()) {
                    floatingLabelShown = !floatingLabelShown
                    getAnimator()!!.start()
                }
            }
        })
    }

    private fun getAnimator(): ObjectAnimator? {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this@MaterialEditText, "floatingLabelFraction", 0f, 1f)
        }
        return animator
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.alpha = (this.floatingLabelFraction * 0xff).toInt()
        canvas.drawText(
            hint.toString(), HORIZONTAL_OFFSET,
            VERTICAL_OFFSET - this.floatingLabelFraction * VERTICAL_OFFSET_EXTRA, paint
        )
    }

    companion object {
        private val TEXT_SIZE = Utils.dp2px(12f)
        private val TEXT_MARGIN = Utils.dp2px(8f)
        private val VERTICAL_OFFSET = Utils.dp2px(38f)
        private val HORIZONTAL_OFFSET = Utils.dp2px(5f)
        private val VERTICAL_OFFSET_EXTRA = Utils.dp2px(16f)
    }
}




