package com.wangyl.learnview.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.widget.FrameLayout
import com.wangyl.learnview.Extensions.logEvent

class EventGroup2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        logEvent(ev, 0)
        when (ev.actionMasked) {
//            ACTION_DOWN -> return false
//            ACTION_MOVE -> return true
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        logEvent(ev, 1)
        when (ev.actionMasked) {
//            ACTION_DOWN -> return false
            ACTION_MOVE -> return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        logEvent(event, 2)
//        when (event.actionMasked) {
//            ACTION_DOWN -> return true
//            ACTION_MOVE -> return true
//        }
        return super.onTouchEvent(event)
    }
}