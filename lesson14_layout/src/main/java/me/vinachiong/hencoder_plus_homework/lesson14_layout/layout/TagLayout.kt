package me.vinachiong.hencoder_plus_homework.lesson14_layout.layout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup


/**
 *
 *
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class TagLayout : ViewGroup {

//    /** 子View间的上下左右边距 */
//    private var childSpace = Utils.dp2px(5f)
//    /** 每行的可显示高度 */
//    private var rowHeight = Utils.dp2px(48f)

    private var childBounds = mutableListOf<Rect>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val parentWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeightMode = MeasureSpec.getMode(heightMeasureSpec)
        val parentHeightSize = MeasureSpec.getSize(heightMeasureSpec)

        // 累计所有Child布局好后，占的最大宽度
        var widthUsed = 0
        // 累计所有Child布局好后，占的高度
        var heightUsed = 0
//        widthUsed += paddingLeft // 加上自身的paddingLeft
//        heightUsed += paddingTop // 加上自身的paddingTop

        var lineWidthUsed = 0
        var lineHeightUsed = 0

        (0 until childCount).forEach { index ->
            val child = getChildAt(index)
            if (child.visibility != View.GONE) {
                // 让Child先measure一次，可获取Child的宽高
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

                if (parentWidthMode != MeasureSpec.UNSPECIFIED && (lineWidthUsed + child.measuredWidth) > parentWidthSize) {
                    lineWidthUsed = 0
                    heightUsed += lineHeightUsed
                    measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
                }

                val rect: Rect
                if (childBounds.size <= index) {
                    rect = Rect()
                    childBounds.add(rect)
                } else {
                    rect = childBounds[index]
                }

                rect.set(
                    lineWidthUsed, heightUsed,
                    lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight
                )

                lineWidthUsed += child.measuredWidth
                widthUsed = Math.max(lineWidthUsed, widthUsed)
                lineHeightUsed = Math.max(lineHeightUsed, child.measuredHeight)
            }
        }
        val measuredWidth = widthUsed
        heightUsed += lineHeightUsed
        val measuredHeight = heightUsed
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        (0 until childCount).forEach { index ->
            val child = getChildAt(index)
            val rect = childBounds[index]
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }
}