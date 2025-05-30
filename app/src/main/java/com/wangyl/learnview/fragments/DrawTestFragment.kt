package com.wangyl.learnview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wangyl.learnview.databinding.FragmentDrawTextBinding
import com.wangyl.learnview.drawables.LoadingDrawable

class DrawTestFragment:Fragment() {

    lateinit var binding: FragmentDrawTextBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDrawTextBinding.inflate(inflater, null, false)
        binding.customView.setOnClickListener {
            Toast.makeText(context, "点击了图片", Toast.LENGTH_LONG).show()
            binding.waterView.speedUp(2)
        }
        binding.speechWaves.start()
        binding.speechWaves.setOnClickListener {
            binding.speechWaves.start()
            Toast.makeText(context, "点击了按钮", Toast.LENGTH_LONG).show()
        }
        binding.testView.background = LoadingDrawable().apply {
            setBounds(binding.testView.left, binding.testView.top, binding.testView.right, binding.testView.bottom)
        }
        binding.waterView.setOnClickListener {
//            binding.waterView.toggleAnimateEnabled()
            binding.waterView.toggleSmallState()
//            binding.waterView.setSpeedDelay(120)
//            binding.waterView.setAnimateEnabled(false)
//            binding.waterView.speedUp(2)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.speechWaves.stop()
    }
}