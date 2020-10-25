package com.qazstudy.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.qazstudy.ui.fragment.TranslateFragment
import com.qazstudy.ui.fragment.WordListFragment

class AdapterDictionaryPage(fm: FragmentManager, private val i: Int) : FragmentPagerAdapter(fm, i) {

    override fun getCount(): Int {
        return i
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 ->  WordListFragment()
            1 ->  TranslateFragment()
            else -> WordListFragment()
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

}