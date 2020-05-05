package com.qazstudy.ui.fragment.lecture

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

class FragmentLectureIntro : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lecture_intro, container, false)
    }
}
