package com.wangyl.learnview

import android.animation.ObjectAnimator
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.wangyl.learnview.Extensions.logEvent
import com.wangyl.learnview.databinding.ActivityScrollingBinding
import com.wangyl.learnview.fragments.TouchEventTestFragment

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

        }
        if (savedInstanceState == null) {
            val fragment = TouchEventTestFragment()
            fragment.arguments = intent.extras
            supportFragmentManager
                .beginTransaction()
                .add(R.id.content_layout, fragment)
                .commit()
        }

        ObjectAnimator
            .ofFloat(binding.fab, View.ROTATION_Y, 0f, 360f)
            .apply {
                duration = 3000
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
            }
            .also { it.start() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        window.decorView.logEvent(ev, -1)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        window.decorView.logEvent(event, -2)
        return super.onTouchEvent(event)
    }
}