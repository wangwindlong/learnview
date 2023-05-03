package com.wangyl.learnview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.wangyl.learnview.R

class CustomDrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var mDrawable: Drawable? = null
    private val path = Path()
    private val paint = Paint()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomDrawView)
        mDrawable = ResourcesCompat.getDrawable(resources,
            typeArray.getResourceId(R.styleable.CustomDrawView_android_background, R.drawable.gallery_photo_8), null)
        typeArray.recycle()
        Log.d("CustomDrawView", "mDrawable=$mDrawable")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = mDrawable?.intrinsicWidth ?: 0
        val height = mDrawable?.intrinsicHeight ?: 0
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawARGB(50, 255, 0,0)
        canvas.save()
        canvas.translate((-50).toFloat(), (-50).toFloat())
        canvas.rotate(180.toFloat(), measuredWidth / 2f, measuredHeight / 2f)
        mDrawable?.apply {
            setBounds(paddingLeft, paddingTop, measuredWidth - paddingRight, measuredHeight - paddingBottom)
            draw(canvas)
        }
        paint.color = resources.getColor(R.color.green)
        path.reset()
        val radius = (measuredWidth / 2f).coerceAtMost(measuredHeight / 2f)
        path.addCircle(measuredWidth / 2f, measuredHeight / 2f, radius, Path.Direction.CCW)
        canvas.drawPath(path, paint)
        canvas.restore()
    }
}