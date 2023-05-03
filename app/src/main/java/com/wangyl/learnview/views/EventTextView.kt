package com.wangyl.learnview.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import com.wangyl.learnview.Extensions.logEvent

class EventTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
//) : androidx.appcompat.widget.AppCompatButton(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        logEvent(ev, 0)
//        when (ev.actionMasked) {
//            ACTION_DOWN -> return false
////            ACTION_MOVE -> return true
//        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        logEvent(ev, 2)
//        when (ev.actionMasked) {
//            ACTION_DOWN -> return false
////            ACTION_MOVE -> return true
//        }
        return true
//        return super.onTouchEvent(ev)
    }
}