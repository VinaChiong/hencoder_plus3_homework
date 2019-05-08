package me.vinachiong.hencoder_plus_homework.lesson13_bitmap_drawable.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextPaint
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

    private val paddingRect = Rect()
    private val textPaint = TextPaint()
    private var mLabelTextSize = DEFAULT_LABEL_TEXT_SIZE
    private var labelDrawPoint = PointF()
    private var labelVerticalOffsetExtra = HINT_VERTICAL_OFFSET_EXTRA
    private var useFloatingLabel = true
    private var floatingLabelShown = false
    var floatingLabelFraction: Float = 0f

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

        calculateLabelSize()
        background.getPadding(paddingRect)
        if (useFloatingLabel) {
            setPadding(
                paddingRect.left,
                (paddingRect.top + textSize + HINT_TEXT_MARGIN).toInt(),
                paddingRect.right,
                paddingRect.bottom
            )
        } else {
            setPadding(paddingRect.left, paddingRect.top, paddingRect.right, paddingRect.bottom)
        }

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (floatingLabelShown && s.isEmpty()) {
                    floatingLabelShown = !floatingLabelShown
                    getAnimator().reverse()
                } else if (!floatingLabelShown && s.isNotEmpty()) {
                    floatingLabelShown = !floatingLabelShown
                    getAnimator().start()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private var animator: ObjectAnimator? = null
    private fun getAnimator(): ObjectAnimator {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "floatingLabelFraction", 1f, 0f)
        }
        return animator!!
    }

    private fun calculateLabelSize() {
        if (textSize <= mLabelTextSize) {
            mLabelTextSize = textSize - Utils.dp2px(1f)
        }
        if (mLabelTextSize < 0) {
            mLabelTextSize = DEFAULT_LABEL_TEXT_SIZE
        }

        textPaint.textSize = mLabelTextSize
        labelDrawPoint.x = paddingLeft.toFloat()
        labelDrawPoint.y = mLabelTextSize + HINT_TEXT_MARGIN
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.alpha = (0xff * (1f - floatingLabelFraction)).toInt()
        canvas.drawText(hint.toString(), labelDrawPoint.x, labelDrawPoint.y + floatingLabelFraction * labelVerticalOffsetExtra, textPaint)
    }


    companion object {
        private val PADDING_TOP = Utils.dp2px(13f)
        private val DEFAULT_LABEL_TEXT_SIZE = Utils.dp2px(14f)

        private val HINT_TEXT_MARGIN = Utils.dp2px(8f)
        private val HINT_VERTICAL_OFFSET = Utils.dp2px(38f)
        private val HINT_HORIZONTAL_OFFSET = Utils.dp2px(5f)
        private val HINT_VERTICAL_OFFSET_EXTRA = Utils.dp2px(20f)
    }
}




