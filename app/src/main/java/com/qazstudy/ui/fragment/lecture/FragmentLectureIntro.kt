package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.qazstudy.R
import com.qazstudy.databinding.FragmentLectureIntroBinding
import com.qazstudy.util.viewBinding

class FragmentLectureIntro : Fragment(R.layout.fragment_lecture_intro) {

    private val binding by viewBinding(FragmentLectureIntroBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
