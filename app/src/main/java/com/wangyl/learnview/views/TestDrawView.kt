package com.wangyl.learnview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.wangyl.learnview.Extensions.logEvent
import com.wangyl.learnview.R

class TestDrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val blueColor = ContextCompat.getColor(context, R.color.blue_layer1_primary)

    private var mPaint = Paint().apply {
            color = blueColor
        style = Paint.Style.STROKE
    }

    private var centerX: Int = 0
    private var centerY: Int = 0
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), 40f, mPaint)
    }
}