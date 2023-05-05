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
    private var gray = true
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
        val saved = canvas.saveLayer(null, if (gray) mPaint else mOriginPaint)
        super.dispatchDraw(canvas)
        canvas.restoreToCount(saved)
    }

    override fun onDraw(canvas: Canvas) {
        val saved = canvas.saveLayer(null, if (gray) mPaint else mOriginPaint)
        super.onDraw(canvas)
        canvas.restoreToCount(saved)
    }

    fun setGray(isGray: Boolean) {
        this.gray = isGray
        invalidate()
    }

    fun updateTheme() {
        gray = !gray
        invalidate()
    }
}