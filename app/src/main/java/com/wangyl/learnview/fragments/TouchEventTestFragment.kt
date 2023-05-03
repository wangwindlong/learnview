package com.wangyl.learnview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wangyl.learnview.databinding.FragmentEventTestBinding

class TouchEventTestFragment: Fragment() {
    lateinit var binding: FragmentEventTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEventTestBinding.inflate(inflater)
        return binding.root
    }


}