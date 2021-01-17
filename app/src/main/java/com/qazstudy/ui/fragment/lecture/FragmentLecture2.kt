package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLecture2Binding
import com.qazstudy.util.viewBinding

class FragmentLecture2 : BaseFragmentLecture(R.layout.fragment_lecture2) {

    private val binding by viewBinding(FragmentLecture2Binding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setTheme() {}
}
