package com.qazstudy.ui.fragment.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.qazstudy.R
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.fragment_lecture4.lecture_4_text

class FragmentLecture4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lecture4, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHeaderText()

        if (isDark) {
            lecture_4_text.setTextColor(requireContext().getColor(R.color.white))
        }

        lecture_4_text
    }

    private fun setHeaderText() {
        val txt = activity?.findViewById<TextView>(R.id.activity_lesson__txt_lecture_header)

        val ar = resources.getStringArray(R.array.lessons_header)
        txt?.text = ar[4]
    }
}
