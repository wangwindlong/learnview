package com.wangyl.learnview.drawables

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.Property


class LoadingDrawable : Drawable(), Animatable {
    var mAngel = 0f
    var mCenterX = 0f
    var mCenterY = 0f
    var mRadius = 100f
    private var mValueAnimator: ValueAnimator? = null
    private val mCirClePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#B3BBD5")
        strokeWidth = 10f
    }
    private val mArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#D9FFFFFF")
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
    }
    private val mRect = RectF()

    // 自定义一个扩散半径属性
    private var mRadiusProperty = object : Property<LoadingDrawable, Float>(Float::class.java, "") {
        override fun get(target: LoadingDrawable): Float {
            return target.mRadius
        }

        override fun set(target: LoadingDrawable?, value: Float) {
            target?.mRadius = value
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        mCenterX = bounds.width() / 2f
        mCenterY = bounds.height() / 2f
        mRadius = Math.min(bounds.width(), bounds.height()) / 2f
        mRect.left = mCenterX - mRadius
        mRect.top = mCenterY - mRadius
        mRect.right = mCenterX + mRadius
        mRect.bottom = mCenterY + mRadius
        if (isRunning()) {
            stop()
        }
        // 控制扩散半径的属性变化
        val radiusHolder = PropertyValuesHolder.ofFloat(mRadiusProperty, 0f, mRadius)
        // 控制透明度的属性变化
        val alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0);
        mValueAnimator = ObjectAnimator.ofPropertyValuesHolder(this, radiusHolder, alphaHolder)
        mValueAnimator?.setStartDelay(1000)
        mValueAnimator?.setDuration(1200)
        mValueAnimator?.addUpdateListener {invalidateSelf() }
        mValueAnimator?.setRepeatMode(ValueAnimator.RESTART)
        mValueAnimator?.setRepeatCount(ValueAnimator.INFINITE)
        start()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.parseColor("#FF0000"))
//        canvas.rotate(mAngel, mCenterX, mCenterY)
        Log.d("LoadingDrawable", "draw mCenterX=$mCenterX mCenterY=$mCenterY mRadius=$mRadius")
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirClePaint)
//        canvas.drawArc(mRect, 0f, 20f, false, mArcPaint)
    }

    override fun setAlpha(alpha: Int) {
        mCirClePaint.alpha = alpha
        mArcPaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
//        mCirClePaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun start() {
        mValueAnimator?.start()
    }

    override fun stop() {
        mValueAnimator?.end()
    }

    override fun isRunning(): Boolean {
        return mValueAnimator?.isRunning() ?: false
    }
}