package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLecture5Binding
import com.qazstudy.util.viewBinding

class FragmentLecture5 : BaseFragmentLecture(R.layout.fragment_lecture5) {

    private val binding by viewBinding(FragmentLecture5Binding::bind)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun setTheme() {}

}
