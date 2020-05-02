package com.qazstudy.ui.fragment.lecture

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lecture1.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class FragmentLecture8 : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lecture8, container, false)
    }
}
