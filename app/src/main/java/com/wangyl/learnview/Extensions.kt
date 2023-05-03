package com.wangyl.learnview

import android.app.Activity
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View

object Extensions {
    fun logd(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun Activity.logEvent(event: MotionEvent?, type: Int) {
        val touchType = when(type) {
            -2 -> "Activity onTouchEvent"
            -1 -> "Activity dispatchTouchEvent"
            else -> "Activity ACTION_UNKNOWN"
        }
        val tag = window.decorView
        when (event?.actionMasked) {
            ACTION_DOWN -> logd(tag.javaClass.simpleName, "$touchType ACTION_DOWN")
            ACTION_UP -> logd(tag.javaClass.simpleName, "$touchType ACTION_UP")
            ACTION_MOVE -> logd(tag.javaClass.simpleName, "$touchType ACTION_MOVE")
            ACTION_CANCEL -> logd(tag.javaClass.simpleName, "$touchType ACTION_CANCEL")
            else -> logd(tag.javaClass.simpleName, "$touchType ACTION_UNKNOWN")
        }
    }
    fun View.logEvent(event: MotionEvent?, type: Int) {
        val touchType = when(type) {
            -2 -> "Activity onTouchEvent"
            -1 -> "Activity dispatchTouchEvent"
            0 -> "dispatchTouchEvent"
            1 -> "onInterceptTouchEvent"
            2 -> "onTouchEvent"
            else -> "ACTION_UNKNOWN"
        }
        val className = javaClass.simpleName
        when (event?.actionMasked) {
            ACTION_DOWN -> logd(className, "$className $touchType ACTION_DOWN")
            ACTION_UP -> logd(className, "$className $touchType ACTION_UP")
            ACTION_MOVE -> logd(className, "$className $touchType ACTION_MOVE")
            ACTION_CANCEL -> logd(className, "$className $touchType ACTION_CANCEL")
            else -> logd(className, "$className $touchType ACTION_UNKNOWN")
        }
    }
}