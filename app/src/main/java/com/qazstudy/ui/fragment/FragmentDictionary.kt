package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.qazstudy.ui.adapter.AdapterDictionaryPage
import kotlinx.android.synthetic.main.fragment_dictionary.*

class FragmentDictionary : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pageAdapter = AdapterDictionaryPage(
            requireActivity().supportFragmentManager,
            tab_layout.tabCount)

        view_pager.adapter = pageAdapter

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem = tab!!.position
                pageAdapter.notifyDataSetChanged()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })

        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }
}