package com.wangyl.learnview

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.wangyl.learnview.databinding.ActivityMainBinding
import com.wangyl.learnview.fragments.MainFragment
import com.wangyl.learnview.views.GrayFrameLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var grayFrameLayout: GrayFrameLayout? = null
    private var mIsGray = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        binding.toolbar.title = title
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = resources.getColor(R.color.gray)
        }
        if (savedInstanceState == null) {
            Utils.showFragment(this, MainFragment::class.java.name)
        }

        binding.fab.setOnClickListener {
            AlertDialog.Builder(this).setMessage("切换风格").setNegativeButton("确定") { dialog, which ->
                mIsGray = !mIsGray
                recreate()
                dialog.dismiss()
            }.create().show()
            binding.fab.animate().rotationYBy(360f).setDuration(270).start()
        }


    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.d("test", "onCreateView")
        try {
            if ("FrameLayout" == name) {
                val count: Int = attrs.attributeCount
                for (i in 0 until count) {
                    val attributeName: String = attrs.getAttributeName(i)
                    val attributeValue: String = attrs.getAttributeValue(i)
                    if (attributeName == "id") {
                        val id = attributeValue.substring(1).toInt()
                        val idVal = resources.getResourceName(id)
                        if ("android:id/content" == idVal) {
                            return GrayFrameLayout(context, attrs).apply {
                                grayFrameLayout = this
                                setGray(mIsGray)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onCreateView(name, context, attrs)
    }


}