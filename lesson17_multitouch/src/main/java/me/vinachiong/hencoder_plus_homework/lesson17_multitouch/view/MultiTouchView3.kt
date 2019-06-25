package me.vinachiong.hencoder_plus_homework.lesson17_multitouch.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.TextPaint
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import me.vinachiong.lib.Utils

/**
 *
 *
 * @author changweiliang@kungeek.com
 * @version v1.2.0
 */
class MultiTouchView3 : View {

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val paths = SparseArray<Path>(0)

    constructor(context: Context?) : super(context) {
        init(null, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, null)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int?) {
        textPaint.textSize = TITLE_TEXT_SIZE
        paint.strokeWidth = Utils.dp2px(5f)
        paint.style = Paint.Style.STROKE
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val p = Path()
                p.moveTo(event.getX(event.actionIndex), event.getY(event.actionIndex))
                paths.append(event.getPointerId(event.actionIndex), p)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                (0 until event.pointerCount).forEach {
                    val pId = event.getPointerId(it)
                    paths[pId].lineTo(event.getX(it), event.getY(it))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                paths.remove(event.getPointerId(event.actionIndex))
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        (0 until paths.size()).forEach {
            canvas.drawPath(paths.valueAt(it), paint)
        }
    }

    companion object {
        private const val TAG = "MultiTouchView3"
        private const val TITLE = "多点绘制画板"
        private val TITLE_TEXT_SIZE = Utils.dp2px(20f)
    }
}