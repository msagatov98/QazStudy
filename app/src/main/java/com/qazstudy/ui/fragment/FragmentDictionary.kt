package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.qazstudy.R
import com.qazstudy.databinding.FragmentDictionaryBinding
import com.qazstudy.ui.adapter.AdapterDictionaryPage
import com.qazstudy.util.viewBinding

class FragmentDictionary : Fragment(R.layout.fragment_dictionary) {

    private val binding by viewBinding(FragmentDictionaryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pageAdapter = AdapterDictionaryPage(
            requireActivity().supportFragmentManager,
            binding.tabLayout.tabCount
        )
        binding.viewPager.adapter = pageAdapter
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position
                pageAdapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
    }
}