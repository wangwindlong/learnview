package com.wangyl.learnview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.wangyl.learnview.Utils
import com.wangyl.learnview.databinding.FragmentMainBinding
import com.wangyl.learnview.databinding.MainItemBinding

class MainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, null, false)
        binding.mainRv.adapter = MainAdapter(requireActivity())
        return binding.root
    }

    class MainAdapter(private val activity: FragmentActivity) : RecyclerView.Adapter<MainHolder>() {
        private val list = arrayListOf(
            MainItem("Event测试", TouchEventTestFragment::class.java.name),
            MainItem("绘制测试", DrawTestFragment::class.java.name),
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context))
            return MainHolder(binding)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val item = list[position]
            holder.bind(item)
            (holder.binding as? MainItemBinding)?.itemTv?.apply {
                text = item.name
                setOnClickListener {
                    Utils.showFragment(activity, item.fragment)
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

    class MainHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainItem) {


        }
    }

    data class MainItem(val name: String, val fragment: String)
}