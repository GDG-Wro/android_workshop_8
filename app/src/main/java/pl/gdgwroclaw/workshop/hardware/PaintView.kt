package pl.gdgwroclaw.workshop.hardware

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val path = Path()
    private val paint = Paint().apply {
        color = Color.RED
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f
        style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            path.reset()
            path.moveTo(event.x, event.y)
        } else {
            path.lineTo(event.x, event.y)
        }
        invalidate()
        return true
    }
}
