package com.wangyl.learnview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

class GrayFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var isGray = false
    var mPaint = Paint()
    var mOriginPaint = Paint()

    init {
        mPaint.colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
    }

    override fun draw(canvas: Canvas) {
//        if (isGray) canvas.saveLayer(null, mPaint)
        super.draw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(null, if (isGray) mPaint else mOriginPaint)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.saveLayer(null, if (isGray) mPaint else mOriginPaint)
        super.onDraw(canvas)
        canvas.restore()
    }

    fun setGray(isGray: Boolean) {
        this.isGray = isGray
        invalidate()
    }

    fun updateTheme() {
        isGray = !isGray
        invalidate()
    }
}