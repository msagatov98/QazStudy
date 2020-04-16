package com.qazstudy.ui.fragment

import com.qazstudy.R
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lecture1.*
import com.qazstudy.ui.activity.ActivityNavigation.Companion.isDark

class FragmentLecture1 : Fragment() {

    private lateinit var  arrayAbcCyrillic: Array<TextView>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arrayAbcCyrillic = arrayOf(abc_cyrillic_1, abc_cyrillic_2, abc_cyrillic_3, abc_cyrillic_4, abc_cyrillic_5,
                                abc_cyrillic_6,  abc_cyrillic_7,  abc_cyrillic_8, abc_cyrillic_9, abc_cyrillic_10,
                                abc_cyrillic_11, abc_cyrillic_12, abc_cyrillic_13, abc_cyrillic_14, abc_cyrillic_15,
                                abc_cyrillic_16, abc_cyrillic_17, abc_cyrillic_18, abc_cyrillic_19, abc_cyrillic_20,
                                abc_cyrillic_21, abc_cyrillic_22, abc_cyrillic_23, abc_cyrillic_24, abc_cyrillic_25,
                                abc_cyrillic_26, abc_cyrillic_27, abc_cyrillic_28, abc_cyrillic_29, abc_cyrillic_30,
                                abc_cyrillic_31, abc_cyrillic_32, abc_cyrillic_33, abc_cyrillic_34, abc_cyrillic_35,
                                abc_cyrillic_36, abc_cyrillic_37, abc_cyrillic_38, abc_cyrillic_39, abc_cyrillic_40,
                                abc_cyrillic_41, abc_cyrillic_42, abc_cyrillic_43, abc_cyrillic_44, abc_cyrillic_45)

        if (isDark) {
            text_view.setTextColor(context!!.getColor(R.color.white))
            abc.setBackgroundColor(context!!.getColor(R.color.txt_color))
            arrayAbcCyrillic.forEach {
                it.setTextColor(context!!.getColor(R.color.light_blue))
                it.setBackgroundColor(context!!.getColor(R.color.dark))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lecture1, container, false)
    }
}
