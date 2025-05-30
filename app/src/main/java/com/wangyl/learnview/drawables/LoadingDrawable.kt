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
import kotlin.math.roundToInt


class LoadingDrawable : Drawable(), Animatable {
    var mAngel = 0f
    var mCenterX = 0f
    var mCenterY = 0f
    var mRadius = 100f
    val mAlphaList = mutableListOf(0.5f, 0.3f, 0.1f)
    private var mValueAnimator: ValueAnimator? = null
    private val mCirClePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#88b7f7")
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
        val alphaHolder = PropertyValuesHolder.ofInt("circleAlpha", 255, 0)
        mValueAnimator = ObjectAnimator.ofPropertyValuesHolder(this, radiusHolder, alphaHolder)
//        mValueAnimator?.setStartDelay(1000)
        mValueAnimator?.setDuration(2000)
        mValueAnimator?.addUpdateListener {invalidateSelf() }
        mValueAnimator?.setRepeatMode(ValueAnimator.RESTART)
        mValueAnimator?.setRepeatCount(ValueAnimator.INFINITE)
        start()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.parseColor("#FF0000"))
//        canvas.rotate(mAngel, mCenterX, mCenterY)
        Log.d("LoadingDrawable", "draw mCenterX=$mCenterX mCenterY=$mCenterY mRadius=$mRadius")
//        mCirClePaint.alpha = cacAlpha(2)
        canvas.drawCircle(mCenterX, mCenterY, cacRadius(2) , mCirClePaint)
//        mCirClePaint.alpha = cacAlpha(1)
        canvas.drawCircle(mCenterX, mCenterY, cacRadius(1) , mCirClePaint)
//        mCirClePaint.alpha = cacAlpha(0)
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirClePaint)
//        canvas.drawArc(mRect, 0f, 20f, false, mArcPaint)
    }

    private fun cacRadius(stage: Int): Float {
        val res = mRadius - stage * 25f
        if (res < 0) return 0f
        return res
    }
    private fun cacAlpha(stage: Int): Int {
        return (mAlphaList[stage] * 255).toInt()
    }

    override fun setAlpha(alpha: Int) {
        mCirClePaint.alpha = alpha
        mArcPaint.alpha = alpha
        invalidateSelf()
    }

    private fun setCircleAlpha(alpha: Int) {
        mCirClePaint.alpha = (mAlphaList[getSegmentIndex(255, 0, 3, alpha)] * 255).toInt()
        invalidateSelf()
    }

    fun getSegmentIndex(
        startValue: Int,
        endValue: Int,
        numSegments: Int,
        currentValue: Int
    ): Int {
        if (numSegments <= 0) {
            return 0 // Or throw an exception
        } else if (numSegments == 1) {
            return 1
        }

        val segmentSize = (startValue - endValue).toDouble() / numSegments

        // Calculate the segment index
        val segmentIndex = ((startValue - currentValue) / segmentSize).roundToInt()

        // Ensure the index is within the valid range
        return segmentIndex.coerceIn(0, numSegments - 1)
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