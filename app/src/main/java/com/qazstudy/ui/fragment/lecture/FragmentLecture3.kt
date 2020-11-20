package com.qazstudy.ui.fragment.lecture

import com.qazstudy.R
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannedString
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.qazstudy.ui.activity.ActivityNavigation.Companion.mUser
import kotlinx.android.synthetic.main.fragment_lecture3.*

class FragmentLecture3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lecture3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle()

        if (mUser.isDark) {
            lecture_3_text.setTextColor(requireContext().getColor(R.color.white))
        }
    }

    private fun setTitle() {
        val txt = activity?.findViewById<TextView>(R.id.activity_lesson__txt_lecture_header)

        val ar = resources.getStringArray(R.array.lessons_header)
        txt?.text = ar[3]

    }

}