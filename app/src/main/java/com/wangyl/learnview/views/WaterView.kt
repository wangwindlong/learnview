package com.wangyl.learnview.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import kotlin.math.min
import androidx.core.graphics.toColorInt

class WaterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Runnable {

    /**
     * 圆中心点的x和y
     */
    private var centerXPoint: Float = 0f
    private var centerYPoint: Float = 0f

    /**
     * 控件的长宽最小值
     */
    private var squareWidth: Int = 0

    /**
     * 控件中心的尺寸
     */
    private var centerWidth: Int = CENTER_CIRCLE_SIZE
    private var isSmall = false
    private var isAnimateEnabled = true

    /**
     * 圆圈默认颜色
     */
    private var circleColor: Int = CIRCLE_DEFAULT_COLOR


    /**
     * 等圆圈扩散到这个大小后，增加下一个圆圈
     */
    private var centerSpaceSize = CIRCLE_SPACE

    /**
     * 最大的圈数
     */
    private var maxCircleCount = MAX_CIRCLE_COUNT

    /**
     * 圆圈集合和透明度集合  里面存放的是圆圈宽度和透明度
     */
    var circleList = mutableListOf<Int>()
    var circleAlphaList = mutableListOf<Int>()

    /**
     * 圆圈透明度递减步数
     */
    var circleAlphaReduceStep: Int = 0

    /**
     * 圆圈扩散步数
     */
    var diffuseDistance: Int = DIFFUSE_DISTANCE

    /**
     * 圆圈动画时延
     */
    var diffuseDelay: Long = ANIMATE_DELAY_MILLI


    /**
     * 圆圈画笔
     */
    lateinit var circlePaint: Paint

    /**
     * 中心圆ImageView
     */
    var circleImageView: ImageView = ImageView(context);

    var mHandler = Handler(Looper.myLooper()!!)

    /**
     * 中心圆ImageView动画
     */
    override fun run() {
//        var x = ObjectAnimator.ofFloat(circleImageView, "scaleX", 1f, 1.3f, 1f)
//        x.interpolator = OvershootInterpolator();
//
//        var y = ObjectAnimator.ofFloat(circleImageView, "scaleY", 1f, 1.3f, 1f)
//        y.interpolator = OvershootInterpolator();
//        var animatorSet = AnimatorSet()
//
//        animatorSet.setDuration(700)
//        animatorSet.playTogether(x, y);
//        animatorSet.start()

//        mHandler.postDelayed(this, 800)
    }

    init {
        addNewCircle()
        setWillNotDraw(false)
        mHandler.postDelayed(this, 0)

        circlePaint = Paint().apply {
            color = circleColor
        }
    }

    private fun getCircleSpace() : Int {
        return centerSpaceSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerXPoint = (w / 2).toFloat()
        centerYPoint = (h / 2).toFloat()

        /**
         * 计算出从中心点开始递减透明度的步数
         */
        var min = min(w, h)
        squareWidth = min
        circleAlphaReduceStep = calcAlphaStep()
        centerSpaceSize = (squareWidth-centerWidth) / maxCircleCount /2
    }

    private fun calcAlphaStep(): Int {
//        return (255 / (calcSquareWidth() / diffuseDistance)) * 2
        return ((255 / ((calcSquareWidth() - centerWidth) / diffuseDistance)) * 2)
    }

    private fun calcSquareWidth() : Int {
        return (if (isSmall)squareWidth/3 else squareWidth)
    }

    private fun getMaxWidth() : Int {
        return if (isSmall) squareWidth / 3    else squareWidth - 100
    }

    private fun addNewCircle() {
        circleList.add(centerWidth)
        circleAlphaList.add(255)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isAnimateEnabled) {
            drawCircleWaves(canvas)
        }
        // draw other
    }

    private fun drawCircleWaves(canvas: Canvas) {
        /**
         * 遍历所有圆圈，第一次的时候数量是 1
         */
        for (i in circleList.indices) {
            circlePaint.alpha = circleAlphaList[i]
            var circleWidth = circleList[i]
            canvas.drawCircle(centerXPoint, centerYPoint, circleWidth.toFloat(), circlePaint)
            circleList[i] = diffuseDistance + circleList[i]

            /**
             * 设置当前透明度
             */
            circleAlphaList[i] = if (circleAlphaList[i] - circleAlphaReduceStep <= 0) 0 else {
                circleAlphaList[i] - circleAlphaReduceStep
            }
        }

        /**
         * 如果最内圈的宽度大于CENTER_CIRCLE_SIZE，则增加下一个圆
         */
        if (!circleList.isEmpty() && circleList[circleList.lastIndex] > centerWidth + getCircleSpace()) {
            addNewCircle()
        }

        /**
         * 如果最外圈的宽度大于measuredWidth，则删除最外圈
         */
        if (!circleList.isEmpty() && circleList[0] >= getMaxWidth()) {
            circleList.removeAt(0)
            circleAlphaList.removeAt(0)

        }

        /**
         * 延迟75毫秒后开始下一次
         */
        postInvalidateDelayed(diffuseDelay)
    }

    /**
     * 调整水波纹大小
     */
    fun toggleSmallState() {
        isSmall = !isSmall
        centerSpaceSize = (squareWidth-centerWidth) / maxCircleCount /2
        if (isSmall) centerSpaceSize = centerSpaceSize / 3
//        circleList.clear()
//        circleAlphaList.clear()
        addNewCircle()
        circleAlphaReduceStep = calcAlphaStep()
    }

    /**
     * 开关水波纹动画
     */
    fun toggleState() {
        isAnimateEnabled = !isAnimateEnabled
        postInvalidateOnAnimation()
    }

    /**
     * 是否显示水波纹动画
     */
    fun setAnimateEnabled(enable: Boolean) {
        isAnimateEnabled = enable
        if (!enable) {
            circleList.clear()
            circleAlphaList.clear()
            addNewCircle()
        }
        postInvalidateOnAnimation()
    }

    fun getAnimateEnabled():Boolean {
        return isAnimateEnabled
    }

    fun toggleAnimateEnabled() {
        setAnimateEnabled(!isAnimateEnabled)
    }

    /**
     * 动画时延，单位ms
     */
    fun setSpeedDelay(delay: Long) {
        if (delay <= 0) return
        diffuseDelay = delay
    }

    fun setSpeedStep(step: Int) {
        if (step <= 0) return
        diffuseDistance = step
        circleAlphaReduceStep = calcAlphaStep()
    }

    fun speedUp(stepBy: Int) {
        var step = diffuseDistance + stepBy
        if (step <= 0 || step > getCircleSpace()) step = DIFFUSE_DISTANCE
        diffuseDistance = step
        circleAlphaReduceStep = calcAlphaStep()
    }

    companion object {
        const val DEFAULT_CIRCLE_WIDTH = 50
        const val CENTER_CIRCLE_SIZE = 100
        const val CIRCLE_SPACE = 75
        const val ANIMATE_DELAY_MILLI = 75L
        const val MAX_CIRCLE_COUNT = 4
        const val DIFFUSE_DISTANCE = 10
        val CIRCLE_DEFAULT_COLOR: Int = "#FFB6C1".toColorInt()
    }
}