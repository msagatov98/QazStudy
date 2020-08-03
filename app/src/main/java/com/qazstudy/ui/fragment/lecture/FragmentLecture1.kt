package com.qazstudy.ui.fragment.lecture

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.media.MediaPlayer
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lecture1.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark
import kotlinx.android.synthetic.main.activity_lesson.*
import kotlinx.android.synthetic.main.activity_lesson.view.*

class FragmentLecture1 : Fragment() {

    private lateinit var  arrayAbcLatin: Array<TextView>
    private lateinit var  arrayAbcCyrillic: Array<TextView>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val txt = activity?.findViewById<TextView>(R.id.activity_lesson__txt_lecture_header)

        val ar = resources.getStringArray(R.array.lessons_header)
        txt?.text = ar[1]

        arrayAbcCyrillic = arrayOf(
            abc_cyrillic_1, abc_cyrillic_2, abc_cyrillic_3, abc_cyrillic_4, abc_cyrillic_5,
            abc_cyrillic_6,  abc_cyrillic_7,  abc_cyrillic_8, abc_cyrillic_9, abc_cyrillic_10,
            abc_cyrillic_11, abc_cyrillic_12, abc_cyrillic_13, abc_cyrillic_14, abc_cyrillic_15,
            abc_cyrillic_16, abc_cyrillic_17, abc_cyrillic_18, abc_cyrillic_19, abc_cyrillic_20,
            abc_cyrillic_21, abc_cyrillic_22, abc_cyrillic_23, abc_cyrillic_24, abc_cyrillic_25,
            abc_cyrillic_26, abc_cyrillic_27, abc_cyrillic_28, abc_cyrillic_29, abc_cyrillic_30,
            abc_cyrillic_31, abc_cyrillic_32, abc_cyrillic_33, abc_cyrillic_34, abc_cyrillic_35,
            abc_cyrillic_36, abc_cyrillic_37, abc_cyrillic_38, abc_cyrillic_39, abc_cyrillic_40,
            abc_cyrillic_41, abc_cyrillic_42)

        arrayAbcLatin = arrayOf(
            abc_latin_1, abc_latin_2, abc_latin_3, abc_latin_4, abc_latin_5,
            abc_latin_6, abc_latin_7, abc_latin_8, abc_latin_9, abc_latin_10,
            abc_latin_11, abc_latin_12, abc_latin_13, abc_latin_14, abc_latin_15,
            abc_latin_16, abc_latin_17, abc_latin_18, abc_latin_19, abc_latin_20,
            abc_latin_21, abc_latin_22, abc_latin_23, abc_latin_24, abc_latin_25,
            abc_latin_26, abc_latin_27, abc_latin_28, abc_latin_29, abc_latin_30,
            abc_latin_31, abc_latin_32)

        if (isDark) {
            text_view.setTextColor(requireContext().getColor(R.color.white))
            arrayAbcCyrillic.forEach {
                it.setTextColor(requireContext().getColor(R.color.light_blue))
            }

            arrayAbcLatin.forEach {
                it.setTextColor(requireContext().getColor(R.color.light_blue))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lecture1, container, false)
    }
}