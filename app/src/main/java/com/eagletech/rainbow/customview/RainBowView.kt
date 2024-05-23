package com.eagletech.rainbow.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.eagletech.rainbow.MainActivity.Companion.paint
import com.eagletech.rainbow.MainActivity.Companion.path

class RainBowView : View {
    var pr: ViewGroup.LayoutParams? = null

    companion object {
        var paths = ArrayList<Path>()
        var colors = ArrayList<Int>()
        var brush = Color.BLACK
    }

    constructor(context: Context) : this(context, null) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        paint.isAntiAlias = true
        paint.color = brush
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 8f
        pr = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                paths.add(path)
                colors.add(brush)
                postInvalidate()
                return true
            }
            else -> return false
        }
    }


    override fun onDraw(canvas: Canvas) {
        for (i in paths.indices) {
            paint.color = colors[i]
            canvas.drawPath(paths[i], paint)
        }
    }
}
