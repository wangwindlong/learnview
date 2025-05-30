package com.wangyl.learnview.views

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import com.wangyl.learnview.R
import kotlin.math.pow
import kotlin.math.sqrt

class RecordButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : View(context, attrs, defStyleAttr) {
    private val rippleColor: Int
    private val rippleSpeed: Float
    private val rippleWidth: Float
    private val rippleDistance: Float

    private val ripplePaint: Paint
    private var rippleAnimator: ValueAnimator? = null
    private var rippleRadius = 0f
    private var centerX = 0
    private var centerY = 0

    private val buttons: MutableList<Button> = ArrayList<Button>() // 存储按钮

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecordButton)
        rippleColor = typedArray.getColor(R.styleable.RecordButton_rippleColor, Color.BLUE)
        rippleSpeed = typedArray.getFloat(R.styleable.RecordButton_rippleSpeed, 1.0f)
        rippleWidth = typedArray.getDimension(R.styleable.RecordButton_rippleWidth, 5.0f)
        rippleDistance = typedArray.getDimension(R.styleable.RecordButton_rippleDistance, 200.0f)
        typedArray.recycle()

        ripplePaint = Paint()
        ripplePaint.setColor(rippleColor)
        ripplePaint.setStyle(Paint.Style.STROKE)
        ripplePaint.setStrokeWidth(rippleWidth)

        // 初始化按钮
        initButtons()
    }

    private fun initButtons() {
        // 创建左、上、右三个按钮
        val leftButton = createButton("左", 0)
        val upButton = createButton("上", 1)
        val rightButton = createButton("右", 2)

        buttons.add(leftButton)
        buttons.add(upButton)
        buttons.add(rightButton)
    }

    private fun createButton(text: String?, index: Int): Button {
        val button = Button(getContext())
        button.setText(text)
        // 设置按钮的位置和大小
        // ...
        button.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                // 处理按钮点击事件
                // ...
            }
        })
        return button
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> startRippleAnimation()
            MotionEvent.ACTION_MOVE -> {
                val distance = calculateDistance(event.getX(), event.getY())
                if (distance > 150) {
                    showButtons()
                    highlightButton(event.getX(), event.getY())
                }
            }

            MotionEvent.ACTION_UP -> {
                stopRippleAnimation()
                hideButtons()
            }
        }
        return true
    }

    private fun calculateDistance(x: Float, y: Float): Float {
        return sqrt((x - centerX).toDouble().pow(2.0) + (y - centerY).toDouble().pow(2.0)).toFloat()
    }

    private fun startRippleAnimation() {
        rippleRadius = 0f
        rippleAnimator = ValueAnimator.ofFloat(0f, rippleDistance)
        rippleAnimator!!.setInterpolator(AccelerateInterpolator())
        rippleAnimator!!.setDuration(1000)
        rippleAnimator!!.addUpdateListener(object : AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator) {
                rippleRadius = animation.getAnimatedValue() as Float
                invalidate() // 重新绘制
            }
        })
        rippleAnimator!!.start()
    }

    private fun stopRippleAnimation() {
        if (rippleAnimator != null) {
            rippleAnimator!!.cancel()
        }
    }

    private fun showButtons() {
        // 显示按钮
        for (button in buttons) {
            button.setVisibility(VISIBLE)
        }
    }

    private fun hideButtons() {
        // 隐藏按钮
        for (button in buttons) {
            button.setVisibility(GONE)
        }
    }

    private fun highlightButton(x: Float, y: Float) {
        // 遍历按钮，判断是否在按钮区域内
        for (button in buttons) {
            if (isPointInButton(x, y, button)) {
                // 高亮按钮
                button.setBackgroundColor(Color.YELLOW)
            } else {
                // 恢复按钮颜色
                button.setBackgroundColor(Color.WHITE)
            }
        }
    }

    private fun isPointInButton(x: Float, y: Float, button: Button): Boolean {
        // 获取按钮的坐标
        val location = IntArray(2)
        button.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1]
        val right = left + button.getWidth()
        val bottom = top + button.getHeight()

        // 判断点是否在按钮区域内
        return x > left && x < right && y > top && y < bottom
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制水波纹
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), rippleRadius, ripplePaint)
    }
}
