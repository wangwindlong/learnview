package com.wangyl.learnview

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
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
//        val view: View = window.decorView
//        val paint = Paint()
//        val cm = ColorMatrix()
//        cm.setSaturation(0f)
//        paint.colorFilter = ColorMatrixColorFilter(cm)
//        view.setLayerType(View.LAYER_TYPE_HARDWARE, paint)

        binding.fab.setOnClickListener {
            AlertDialog.Builder(this).setMessage("切换风格").setNegativeButton("确定") { dialog, which ->
                grayFrameLayout?.updateTheme()
                recreate()
                dialog.dismiss()
            }.create().show()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
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
                            return GrayFrameLayout(context, attrs).apply { grayFrameLayout = this }
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