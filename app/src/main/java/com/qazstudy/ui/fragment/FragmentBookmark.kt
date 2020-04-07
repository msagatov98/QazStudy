package com.qazstudy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qazstudy.ui.activity.Navigation.Companion.isDark
import com.qazstudy.R
import kotlinx.android.synthetic.main.fragment_bookmark.*

class FragmentBookmark : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isDark) {
            fragment_bookmark_tab_layout.background = resources.getDrawable(R.color.light_blue)
            fragment_bookmark__constraint_layout.background = resources.getDrawable(R.color.dark)
        } else {
            fragment_bookmark_tab_layout.background = resources.getDrawable(R.color.colorPrimary)
            fragment_bookmark__constraint_layout.background = resources.getDrawable(R.color.white)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }
}
