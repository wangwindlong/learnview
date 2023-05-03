package com.wangyl.learnview

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

object Utils {
    fun showFragment(activity: FragmentActivity, name: String, params: Bundle? = null) {
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            activity.javaClass.classLoader!!, name)
        fragment.arguments = params
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, fragment, name)
            .addToBackStack(null)
            .commit()
    }
}