package com.wangyl.learnview.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.wangyl.learnview.Extensions.logEvent

class EventGroup1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        logEvent(ev, 0)
//        when (ev.actionMasked) {
//            MotionEvent.ACTION_DOWN -> return false
////            ACTION_MOVE -> return true
//        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        logEvent(ev, 1)
//        when (ev.actionMasked) {
//            MotionEvent.ACTION_DOWN -> return false
////            ACTION_MOVE -> return true
//        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        logEvent(event, 2)
        return super.onTouchEvent(event)
    }
}